# Github accessor
Rails.configuration.x.github = Github.new(
    endpoint: 'https://github.ugent.be/api/v3',
    site: 'https://github.ugent.be',
    upload_endpoint: 'https://github.ugent.be/api/uploads',
    oauth_token: Rails.application.secrets.github_token,
    auto_pagination: true
)

