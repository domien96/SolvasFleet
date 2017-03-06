server "vopro6.ugent.be",
  user: "documentation",
  roles: %w{web app},
  ssh_options: {
    port: 2002,
    forward_agent: true,
    auth_methods: %w(publickey)
  }

set :deploy_to, '/srv/documentation'
set :repo_tree, 'documentation'

after 'deploy:updated', 'docs:create_release'

namespace :docs do
  task create_release: 'docs:javadocs:generate' do
    on release_roles :all do
      execute :mkdir, "-p", release_path
      execute :cp, "-r", "#{deploy_to}/build/backend/build/docs/javadoc", release_path
      execute :cp, "-r", "#{deploy_to}/build/documentation/*", release_path
    end
  end
  namespace :javadocs do
    task generate: :'git:make_clone' do
      on roles(:all) do
        execute "cd #{deploy_to}/build/backend && ./gradlew javadocs"
      end
    end
  end
end

namespace :git do
  Rake::Task['create_release'].clear_actions
  task create_release: :'git:update' do
    on release_roles :all do
      with fetch(:git_environmental_variables) do
        within repo_path do
          execute :mkdir, "-p", release_path
        end
      end
    end
  end

  desc 'clone git repo to build directory'
  task make_clone: :'git:update' do
    on release_roles :all do
      with fetch(:git_environmental_variables) do
        within repo_path do
          execute :mkdir, "-p", "#{deploy_to}/build"
          MyGit.new.archive_to_path "#{deploy_to}/build"
        end
      end
    end
  end
end

class MyGit < Capistrano::SCM::Git
  def archive_to_path(path)
    git :archive, fetch(:branch), "| #{SSHKit.config.command_map[:tar]} -x -f - -C", path
  end
end
