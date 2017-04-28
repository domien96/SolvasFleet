server "vopro6.ugent.be",
  user: "backend",
  roles: %w{web app},
  ssh_options: {
    port: 2002,
    forward_agent: true,
    auth_methods: %w(publickey)
  }

set :deploy_to, '/srv/backend'
set :repo_tree, 'backend'

set :linked_files, %w(build/libs/application.properties)

after 'deploy:updated', 'systemd:solvas:restart'

namespace :systemd do
  namespace :solvas do
    task restart: :'gradle:build' do
      on roles(:all) do
        execute :sudo, :systemctl, :restart, :solvasFleet
      end
    end
  end
end
namespace :gradle do
  task :build do
    on roles(:all) do
      within release_path do
        execute './gradlew', :bootRepackage
      end
    end
  end
end
