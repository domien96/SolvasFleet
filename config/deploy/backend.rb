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

after 'deploy:updated', 'gradle:build'

namespace :gradle do
  task :build do
    on roles(:all) do
      within release_path do
        execute './gradlew', :bootRepackage
      end
    end
  end
end
