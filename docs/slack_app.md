#  Setting up slack app

## Create the New App


create app at [https://api.slack.com/apps](https://api.slack.com/apps) by clicking `"Create New App"`

!!! note "Select `"From an app manifest"`"
    ![create_an_app](images/create_an_app.png)

!!! note "Copy&Paste to App Manifest JSON (JSON is below this image)"
    ![enter_app_manifest](images/enter_app_manifest.png)

```json title="App Manifest"
--8<-- "./docs/app_manifest.json"
```

!!! note "click `"Create"` to confirm"
    ![create_an_app_confirm](images/create_an_app_confirm.png)

## Set App Icon (optional)
!!! tip ""
    ![minecraft_icon_for_slack_app](images/minecraft_icon_for_slack_app.png){ align=left width="200" }
    Setting the AppIcon to this image will further improve the look of the post when updating the topic 🥴

## Get Oauth Token
!!! note "click `"Install to Workspace"`"
    ![install_to_workspace](images/install_to_workspace.png)
!!! note "copy Bot User Oauth Token from "Oauth & Permissions" section"
    ![get_oauth_token](images/get_oauth_token.png)

paste to `SlackToken` in `config.yml`

## Get App-Level Token
!!! note "click `"Generate Token and Scopes"` from "Basic Information" section"
    ![generate_app_level_token](images/generate_app_level_token.png)

!!! note "After setting the Name and Scopes as shown in the image, click the `"Generate"`"
    ![select_app_level_token_scopes](images/select_app_level_token_scopes.png)

!!! note "copy Token"
    ![get_app_level_roken](images/get_app_level_roken.png)

paste to `SlackSocketToken` in `config.yml`

## Get Channel Id

!!! note "In slack, right-click on the channel you want to connect and select `"View Channnel Details"`"
    ![slack_channel_details](images/slack_channel_details.png)

!!! note "copy the channel ID as they are listed at the bottom of the `"about"` tab."
    ![get_slack_channel_id](images/get_slack_channel_id.png)

paste to `SlackChannelId` in `config.yml`

## invite bot to Slack Channel
!!! note "On the slack channel you want to connect to, type `/invite` and select `"Add apps to this channel"` add `Minecraft server`."
    ![invite_bot_to_channel](images/invite_bot_to_channel.png)