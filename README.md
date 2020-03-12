Collaborative network Maze Game

Joint project by Anthony Monte and Cade Cuddy.

Our software is a web-based collaborative maze game where many players are able to connect over a network and “vote” on a sprite’s path through a randomly generated maze. The users will have 4 directions to vote from every 10-15 seconds in order to complete the maze and will have to work together to vote on the correct directions. The highest percentage voted direction will be the direction the sprite moves. The software will mainly be a server-side application that is run on a host computer which will act as a server in which other people connect to through a web browser.

A "Twitch Plays Pokemon" esque game.

"Test Application" contains the client interface for controlling the voting on the sprite's movement.

"Test Server" holds the code that creates the server off of the port-forwarded localhost machine and creates the maze, paints the GUI, and tracks movement until the users implicitly beat the maze.
