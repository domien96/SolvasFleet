Devise.setup do |config|
  config.mailer_sender = 'please-change-me-at@example.com'

  require 'devise/orm/active_record'

  config.case_insensitive_keys = [:email]
  config.strip_whitespace_keys = [:email]

  config.skip_session_storage = [:http_auth]

  config.sign_out_via = :delete

  config.omniauth(:github,
                  Rails.application.secrets.github_app_id,
                  Rails.application.secrets.github_app_secret,
                  {
                      :client_options => {
                          :site => 'https://github.ugent.be/api/v3',
                          :authorize_url => 'https://github.ugent.be/login/oauth/authorize',
                          :token_url => 'https://github.ugent.be/login/oauth/access_token',
                      },
                      scope: 'user, public_repo'
                  })
end
