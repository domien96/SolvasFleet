require "capistrano/scm/git"

server "vopro6.ugent.be",
   user: "snapshot",
   roles: %w{web app},
   ssh_options: {
     port: 2002,
     forward_agent: true,
     auth_methods: %w(publickey)
   }

set :deploy_to, '/srv/snapshot'

namespace :git do
  Rake::Task['create_release'].clear_actions
  desc 'Zip repo and copy to releases'
  task create_release: :'git:update' do
     on release_roles :all do
       with fetch(:git_environmental_variables) do
         within repo_path do
           execute :mkdir, "-p", release_path
           MyGit.new.zip_in_release_path
         end
       end
     end
  end
end


class MyGit < Capistrano::SCM::Git
  def zip_in_release_path
    git :archive, fetch(:branch), '--format zip' "> #{release_path}/snapshot.zip"
  end
end
