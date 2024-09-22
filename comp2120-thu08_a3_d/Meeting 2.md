**Date**: 18/9/2024
**Members Present**
- Mitchell Browne
- Jason Xu
- Guanbo Yang
- Yeshen Gao (scribe)
### Agenda
- Basic User Interface
- Feature Breakdown
- Technical Feasibility
- Prototyping

### Discussions
- Detailed basic feature implementation
	- Movement within the Maze and Lobby
		- There will be several grids representing different rooms or functional building in the Dungeon/Lobby, and the player will move across these grids by clicking up, down, left, or right.
	- Fight with Enemy
		- When within a certain number of grids from an enemy, the player will encounter the enemy and can choose to attack or flee.
		- The location of the enemy need to be set by developers in advance
	- Inventory System
		- Weapons and Items like Healing Potion will all be shown in a small section on the windows.
	- Prototyping
		- We are going to use a modified version of Entity Component System, we focuses mainly on the System part, where each system (like rendering or input) handles specific tasks, making it more "system-centric" without fully using the Entity and Component aspects.

### Plan
- By this Friday, complete the prototyping phase of the game.
- By next Monday, finish all systems related to the features we planned.
- By next Wednesday, add contents like map, NPCs, enemies, and any other content to generate a complete and ideal demo.

### Roles (In the recent work cycle)
- Prototyping (Jason Xu)
- Managing and configuring GitLab repositories (Jason Xu, Mitchell Browne)
- Components/Systems Implementation(All members)
- Documentation(Yeshen Gao, Guanbo Yang)