# *My Personal Project*

My personal project will be a map software where users can create their own maps with mountains, trees, trails, buildings, etc, save them, export them to various file formats, etc. This can be used by **writers**, **gamers**, **hikers**, **architects**, or anyone else who needs a highly customizable map. This project is of interest to me because I've had to look for various map softwares to print trails for hikes and I think it would be interesting to try and create one, especially one where you can design your own landscapes.

## User Stories

*Potential* features:
- As a user, I want to be able to add maps to the application
- I want to be able to edit maps
- I want to be able to delete maps
- As a user, I want to be able to add trees, mountains, and buildings to a map and specify their dimensions and height
- As a user, I want to be able to add trails or routes on the map
- As a user, I want to be able to view all trails or physical features on a map
- As a user, I want to be able to zoom in and zoom out on the map
- As a user, I want to be able to click on an object and see features of the object (eg. height, name)

Phase 2:
- As a user, I want to be able to save all current maps to file (optionally)
- As a user, I want to be able to load all maps from file (optionally)

# Instructions for End User

- You can view the panel that displays the Maps that have already been added to the MapMaker by opening the application and creating a map or loading in some maps from file.
- You can view the panel that displays the Features that have already been added to the CustomMap by selecting a Map and clicking "Manage Map"
- You can generate the first required action related to the user story "Adding multiple maps to the application" by loading in maps or clicking "Create Map" from the homescreen and entering a name more than once.
- You can locate my visual component by clicking "Manage Map" with a map selected. The map will then display any features you create with the "Add Feature" button
- You can save the state of my application by clicking the "Save Maps" button in the main menu
- You can reload the state of my application by clicking the "Load Maps" button in the main menu

# Todo:

- Implement a customizable filepath for saving and loading
- Implement adding MapObject, editing MapObjects via the GUI
- Implement Feature information on hover