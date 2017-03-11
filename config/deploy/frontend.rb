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

set :linked_files, %w(webpack.config.js src/javascripts/constants/constants.ts)


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
        execute :npm, :run, :build
      end
    end
  end
end
