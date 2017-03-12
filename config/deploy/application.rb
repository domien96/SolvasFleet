server "vopro6.ugent.be",
   user: "application",
   roles: %w{web app},
   ssh_options: {
     port: 2002,
     forward_agent: true,
     auth_methods: %w(publickey)
   }

set :deploy_to, '/srv/application'
set :repo_tree, 'backend'
