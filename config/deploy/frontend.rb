server "vopro6.ugent.be",
  user: "frontend",
  roles: %w{web app},
  ssh_options: {
    port: 2002,
    forward_agent: true,
    auth_methods: %w(publickey)
  }

set :deploy_to, '/srv/frontend'
set :repo_tree, 'frontend'

after 'deploy:updated', 'npm:build'

set :linked_files, %w(webpack.config.js)
set :linked_dirs, %w(node_modules)


namespace :npm do
  task :install do
    on roles(:all) do
      within release_path do
        execute :npm, :i
      end
    end
  end

  task build: :'npm:install' do
    on roles(:all) do
      within release_path do
        execute :npm, :run, :typings
        execute :npm, :run, :build, raise_on_non_zero_exit: false
      end
    end
  end
end

namespace :deploy do
  namespace :symlink do
  Rake::Task['linked_files'].clear_actions
    desc "Symlinks break webpack, use cp"
    task :linked_files do
      next unless any? :linked_files
      on release_roles :all do
        execute :mkdir, "-p", linked_file_dirs(release_path)

        fetch(:linked_files).each do |file|
          target = release_path.join(file)
          source = shared_path.join(file)
          next if test "[ -L #{target} ]"
          execute :rm, target if test "[ -f #{target} ]"
          execute :cp, source, target
        end
      end
    end
  end
end

