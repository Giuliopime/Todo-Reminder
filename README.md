# Todo & Reminder
A simple To Do &amp; Reminder Discord Bot made with JDA.  

## Useful links
- [Invite the bot to your server](https://discord.com/oauth2/authorize?client_id=763067629023526954&scope=bot&permissions=289792)
- [Support Server](https://discord.com/invite/pE4VfWF)

## Commands
**Todo & Reminder** can be used either in DMs or in servers.  
If you use the bot in a server every command has to be preceded by to prefix, which by default is `t.`, or by the bot mentions (example `@Todo & Reminder help`).  
While if you use the bot commands in DMs the prefix isn't required.  

In the `Example Usage` section of the following tables you can see 2 different types of parameters:  
- Those surrounded by `[]` brackets are mandatory parameters
- Those surrounded by `()` brackets are optional ones  

#### Utility Commands
| Command  |  Description | Example Usage |
|----|----|----|
| help  | Shows all the bot commands  |  `t.help` |
| invite  | Invite the bot to another server  |  `t.invite` |
|  prefix |  See the prefix of the bot or change it |  `t.prefix (new prefix)` |
| info  | Shows some general informations about the bot  | `t.info`  |
|  ping | Check the bots ping  | `t.ping`  |

#### Todo Commands
Some commands accept some specific `--flags`  

To see the possible flags of a command use the `t.help` command:  
Example: `t.help add`  

| Command  |  Description | Example Usage |
|----|-----|-----|
|  lists |  Shows all your Todo Lists |  `t.lists` |
| new  | Create a new Todo List  |  `t.new [list name]` |
| delete  | Delete a Todo List  |  `t.delete (--flags)` |
| show  | Shows all your Todos of a list  | `t.show (--flags)`  |
|  add | Add a Todo to a Todo List  | `t.add [Todo text] (--flags)`  |
|  complete | Mark a Todo as completed  | `t.complete [Todo text / Todo number] (--flags)`  |
|  remove | Deletes a Todo  | `t.remove [Todo text / Todo number] (--flags)`  |  


#### Reminder Commands
To add a reminder use the following syntax:  
`t.remindMe 1.12.2020 16:45 you should do something meaningful right now`  

The bot will send you a DM the 1st of December at 16:45, the text of the DM will be `you should do something meaningful right now`.  
  
The possible `--flags` for this command are:
- `--daily`: This will remind you something every day.  
If I added that flag to the example above the bot would remind me every day at 16:45 starting from the 1st of December (simply by adding `--daily` at the end of the command).
- `--weekly`: Same concept as daily, but weekly :bonk:  

| Command  |  Description | Example Usage |
|----|-----|-----|
|  remindMe |  Set a reminder |  `t.remindMe [time] [reminder text] (--flags)` |