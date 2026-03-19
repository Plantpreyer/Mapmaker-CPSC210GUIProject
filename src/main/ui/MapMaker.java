package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.JSONException;

import model.CustomMap;
import model.exceptions.InvalidInputException;
import model.exceptions.ObjectClassificationException;
import model.feature.MapObject;
import model.feature.MapPoint;
import model.feature.Route;
import persistence.JsonReader;
import persistence.JsonWriter;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Map maker / manager application
// Has a list of map objects that you can select
@ExcludeFromJacocoGeneratedReport
public class MapMaker extends JFrame implements ListSelectionListener {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    CardLayout cards;

    private JList<CustomMap> mapsJList;
    private DefaultListModel<CustomMap> mapsListModel;
    JPanel menuPanel;
    JPanel infoPanel;
    JTextArea infoText;
    JPanel listPanel;
    JPanel buttonPanel;
    JButton newMapButton;
    JButton loadMapsButton;
    JButton manageMapButton;
    // JButton mapInfoButton;
    JButton saveMapsButton;
    // JButton exitButton;

    JPanel mapPanel;
    JPanel mapWrapperPanel;
    JPanel mapInfoPanel;
    JTextArea mapInfoText;
    JPanel mapButtonPanel;
    JButton newFeatureButton;
    JButton editFeatureButton;
    JButton deleteMapButton;
    JButton backButton;

    JTextField nameField = new JTextField();
    JTextField name1Field = new JTextField();
    JTextField x1Field = new JTextField();
    JTextField y1Field = new JTextField();
    JTextField name2Field = new JTextField();
    JTextField x2Field = new JTextField();
    JTextField y2Field = new JTextField();

    Object[] routeQFields;

    private static NumberFormatter intFormatter;
    private static final Border BLACKLINE_BORDER = BorderFactory.createLineBorder(Color.black);

    private List<CustomMap> maps;
    private int selectIndex;
    private CustomMap selectedMap;
    private String cmdString;
    private Scanner input;
    private boolean quit;
    private int spamCount;
    private ConstructorClass cons;
    private boolean isInMap;

    private static final String JSON_LOCATION = "./data/MapData.json";

    // EFFECTS: runs application
    public MapMaker() {
        super("Map Maker");
        initializeFields();
        initializeGraphics();

        runMapMaker();
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields
    private void initializeFields() {
        spamCount = 0;
        selectIndex = 0;
        maps = new ArrayList<>();
        cmdString = "";
        quit = false;
        cons = new ConstructorClass();
        isInMap = false;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(0);

        nf.setGroupingUsed(false);
        intFormatter = new NumberFormatter(nf);
        intFormatter.setValueClass(Integer.class);
        intFormatter.setAllowsInvalid(false);
        intFormatter.setMaximum(Integer.MAX_VALUE);
        intFormatter.setMinimum(0);
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this DrawingEditor will operate, and
    // populates the tools to be used
    // to manipulate this drawing
    private void initializeGraphics() {
        ImageIcon icon = new ImageIcon("./images/icon.png");
        setIconImage(icon.getImage());

        cards = new CardLayout();
        setLayout(cards);

        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setName("menuPanel");

        mapPanel = new JPanel(new BorderLayout());
        mapPanel.setName("mapPanel");

        setupListPanel();
        setupInfoPanel();
        setupButtonPanel();

        setupMapScreen();

        add(menuPanel, "menuPanel");
        add(mapPanel, "mapPanel");

        cards.show(this.getContentPane(), menuPanel.getName());

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates list panel
    private void setupListPanel() {

        listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        mapsListModel = new DefaultListModel<>();
        mapsListModel.addAll(maps);

        // Create the list and put it in a scroll pane.
        mapsJList = new JList<CustomMap>(mapsListModel);
        mapsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mapsJList.setSelectedIndex(0);
        mapsJList.addListSelectionListener(this);

        JScrollPane mapsScrollPane = new JScrollPane(mapsJList);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                BLACKLINE_BORDER, "MAPS");
        title.setTitleJustification(TitledBorder.CENTER);
        listPanel.setBorder(title);

        listPanel.add(mapsScrollPane, BorderLayout.CENTER);

        menuPanel.add(listPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: updates list panel
    private void updateListPanel() {
        mapsListModel = new DefaultListModel<>();
        mapsListModel.addAll(maps);
        saveMapsButton.setEnabled(!mapsListModel.isEmpty());

        mapsJList.setModel(mapsListModel);
    }

    // MODIFIES: this
    // EFFECTS: creates info panel from current fields
    private void setupInfoPanel() {

        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(320, 0));

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                BLACKLINE_BORDER, "INFO");
        title.setTitleJustification(TitledBorder.CENTER);
        infoPanel.setBorder(title);

        infoText = new JTextArea("No maps! Make a new one?");
        infoText.setEditable(false);

        infoPanel.add(infoText, BorderLayout.CENTER);
        // infoText.setMaximumSize(new Dimension(320, 720 - 240));

        menuPanel.add(infoPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: creates button panel with interactions
    private void setupButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(0, 240));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        setupButtons();

        addAButton(newMapButton, buttonPanel);
        addAButton(loadMapsButton, buttonPanel);
        addAButton(manageMapButton, buttonPanel);
        // addAButton(mapInfoButton, buttonPanel);
        addAButton(saveMapsButton, buttonPanel);
        // addAButton(exitButton, buttonPanel);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                BLACKLINE_BORDER, "SELECT FROM: ");
        title.setTitleJustification(TitledBorder.CENTER);
        buttonPanel.setBorder(title);

        infoPanel.add(buttonPanel, BorderLayout.SOUTH);
        onMapSelected(false);
    }

    // MODIFIES: this
    // EFFECTS: names buttons
    @SuppressWarnings("methodlength")
    private void setupButtons() {
        newMapButton = new JButton("New Map");
        newMapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedName = JOptionPane.showInputDialog(null, "Enter Name: ", "New Map: ",
                        JOptionPane.QUESTION_MESSAGE);
                if (selectedName != null && !selectedName.equals("")) {
                    createMap(selectedName);
                    displayMenu();
                }
            }
        });

        loadMapsButton = new JButton("Load Saved Maps");
        loadMapsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to load the saved maps from file, replacing the current maps?",
                        "Load Saved Maps: ", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    loadMapsStateConfirm();
                }
            }
        });

        manageMapButton = new JButton("Manage Map");
        manageMapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    isInMap = true;
                    changeToMapPanel(selectedMap);
                    printSelectInfo();
                    printMapInfo();
                    System.out.println("Editing map.");
                    printManageMenu();
                } catch (InvalidInputException ex) {
                    // impossible
                }

            }
        });
        // mapInfoButton = new JButton("Map Info");

        saveMapsButton = new JButton("Save Maps");
        saveMapsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to save the current maps to the file?",
                        "Save Maps to File: ", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    saveMapsStateConfirm();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds button to container
    private static void addAButton(JButton button, Container container) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(320, 60));
        container.add(button);
    }

    // EFFECTS: changes card to mapPanel
    private void changeToMapPanel(CustomMap map) {
        mapWrapperPanel.removeAll();
        mapWrapperPanel.add(map, BorderLayout.CENTER);
        map.setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        map.setBackground(Color.GREEN);
        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                BLACKLINE_BORDER, map.getName());
        title.setTitleJustification(TitledBorder.CENTER);
        mapWrapperPanel.setBorder(title);

        mapInfoText.setText("");
        printMapInfoConfirm();

        cards.show(this.getContentPane(), mapPanel.getName());
        repaint();
    }

    // EFFECTS: updates the JFrame
    @Override
    public void repaint() {
        revalidate();
        super.repaint();
    }

    // MODIFIES: this
    // EFFECTS: changes selected map when selected on list
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (mapsJList.getSelectedIndex() == -1) {
                onMapSelected(false);
                if (maps.isEmpty()) {
                    infoText.setText("No maps! Make a new one?");
                } else {
                    infoText.setText(null);
                }
            } else {
                onMapSelected(true);
                selectIndex = mapsJList.getSelectedIndex();
                selectedMap = maps.get(selectIndex);
                printMapInfoConfirm();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: enables/disables various buttons depending on passed bool
    private void onMapSelected(boolean selected) {
        manageMapButton.setEnabled(selected);
    }

    // MODIFIES: this
    // EFFECTS: sets up map viewing screen
    private void setupMapScreen() {
        mapWrapperPanel = new JPanel();

        setupMapInfoPanel();
        setupMapButtonPanel();

        mapPanel.add(mapWrapperPanel, BorderLayout.CENTER);
        mapPanel.add(mapInfoPanel, BorderLayout.EAST);
        mapInfoPanel.add(mapButtonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates map info panel
    private void setupMapInfoPanel() {
        mapInfoPanel = new JPanel();
        mapInfoPanel.setLayout(new BorderLayout());
        mapInfoPanel.setPreferredSize(new Dimension(320, 0));

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                BLACKLINE_BORDER, "INFO");
        title.setTitleJustification(TitledBorder.CENTER);
        mapInfoPanel.setBorder(title);

        mapInfoText = new JTextArea();
        mapInfoText.setEditable(false);

        mapInfoPanel.add(mapInfoText, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates button panel for map with interactions
    private void setupMapButtonPanel() {
        mapButtonPanel = new JPanel();
        mapButtonPanel.setPreferredSize(new Dimension(0, 240));
        mapButtonPanel.setLayout(new BoxLayout(mapButtonPanel, BoxLayout.Y_AXIS));

        setupMapButtons();

        addAButton(newFeatureButton, mapButtonPanel);
        addAButton(editFeatureButton, mapButtonPanel);
        addAButton(deleteMapButton, mapButtonPanel);
        addAButton(backButton, mapButtonPanel);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                BLACKLINE_BORDER, "SELECT FROM: ");
        title.setTitleJustification(TitledBorder.CENTER);
        mapButtonPanel.setBorder(title);

        mapInfoPanel.add(mapButtonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: names buttons
    @SuppressWarnings("methodlength")
    private void setupMapButtons() {
        newFeatureButton = new JButton("New Feature");
        newFeatureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFeatureUIPrompt();
            }
        });

        editFeatureButton = new JButton("Edit Feature");
        editFeatureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFeatureUIPrompt();
            }
        });

        deleteMapButton = new JButton("Delete Map");
        deleteMapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,
                        "!!!!! Are you sure?",
                        "DELETE MAP?", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    changeToMenuPanel();
                    deleteMapConfirm();
                }
            }
        });

        backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeToMenuPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: asks user which type of feature they want to make and adds it to map
    private void newFeatureUIPrompt() {
        String[] options = { "Route", "Building", "Tree" };
        int result = JOptionPane.showOptionDialog(null, "Select type of feature:", "New Feature",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        switch (result) {
            case 0:
                newRouteUIPrompt();
                break;
            case 1:
                break;

            case 2:

                break;
            default:
                break;
        }
    }

    // EFFECTS: prompts user for new route details, adds route to map
    @SuppressWarnings("methodlength")
    private void newRouteUIPrompt() {
        setUpRouteUIPrompt();

        String[] options = new String[] { "Add another point", "Finish", "Cancel" };
        int option = JOptionPane.showOptionDialog(null, routeQFields, "New Route", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, null);
        exitPanel: if (option != -1 & option != 2) {
            List<MapPoint> newPoints = new ArrayList<MapPoint>();
            String name = nameField.getText();
            List<String> pointValues = new ArrayList<>();
            pointValues.add(x1Field.getText());
            pointValues.add(y1Field.getText());
            pointValues.add(x2Field.getText());
            pointValues.add(y2Field.getText());
            if (!name.isEmpty() & allValidCoords(pointValues)) {
                String name1 = name1Field.getText();
                String name2 = name2Field.getText();
                int x1 = Integer.parseInt(x1Field.getText());
                int y1 = Integer.parseInt(x1Field.getText());
                newPoints.add(new MapPoint(name1, x1, y1));
                int x2 = Integer.parseInt(x2Field.getText());
                int y2 = Integer.parseInt(x2Field.getText());
                newPoints.add(new MapPoint(name2, x2, y2));
            } else {
                break exitPanel;
            }
            while (option == 0) {
                name1Field.setText("");
                x1Field.setText("");
                y1Field.setText("");
                routeQFields = new Object[] { "point name (optional):",
                        name1Field,
                        "x:",
                        x1Field,
                        "y:",
                        y1Field
                };
                option = JOptionPane.showOptionDialog(null, routeQFields, "New Route", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, null);
                pointValues = new ArrayList<>();
                pointValues.add(x1Field.getText());
                pointValues.add(y1Field.getText());
                if (allValidCoords(pointValues)) {
                    String name1 = name1Field.getText();
                    int x1 = Integer.parseInt(x1Field.getText());
                    int y1 = Integer.parseInt(x1Field.getText());
                    newPoints.add(new MapPoint(name1, x1, y1));
                } else {
                    break exitPanel;
                }
            }
            if (option != -1 & option != 2) {
                selectedMap.addRoute(new Route(name, newPoints));
                updateInfoPanel();
                repaint();
                return;
            }
        }
        showErrorPane();
    }

    // EFFECTS: initializes fields for route ui prompt
    private void setUpRouteUIPrompt() {
        nameField = new JTextField();
        name1Field = new JTextField();
        x1Field = new JTextField();
        y1Field = new JTextField();
        name2Field = new JTextField();
        x2Field = new JTextField();
        y2Field = new JTextField();

        routeQFields = new Object[] { "name:", nameField, "First point name (optional):", name1Field, "x:", x1Field,
                "y:", y1Field, "Second point name (optional):", name2Field, "x:", x2Field, "y:", y2Field };
    }

    // EFFECTS: returns true if all strings in list represent integers and are
    // within coord range
    private boolean allValidCoords(List<String> list) {
        for (String b : list) {
            if (!b.chars().allMatch(Character::isDigit) | b.isEmpty()) {
                return false;
            }
            int bb = Integer.parseInt(b);
            if (bb < 0 | bb > HEIGHT) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: shows a popup indicating an error occurred
    private void showErrorPane() {
        JOptionPane.showMessageDialog(null, "Cancelled: invalid input", "Error",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: switches panel from map to menu
    public void changeToMenuPanel() {
        isInMap = false;
        cards.show(this.getContentPane(), "menuPanel");
    }

    // EFFECTS: updates info panels
    private void updateInfoPanel() {
        infoText.setText("");
        mapInfoText.setText("");
        List<String> mapInfo = selectedMap.mapInfo();
        for (String b : mapInfo) {
            infoText.append(b + "\n");
            mapInfoText.append(b + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles input until quit
    void runMapMaker() {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");

        while (!quit) {
            if (!maps.isEmpty()) {
                selectedMap = maps.get(selectIndex);
            }
            if (isInMap) {
                printManageMenu();
            } else {
                displayMenu();
            }
            try {
                cmdString = takeInput();
                handleInputInMapOrNo();
            } catch (InvalidInputException e) {
                System.out.println("Error: couldn't interpret input.");
                quit = incrementSpam();
                continue;
            }
            spamCount = 0;
        }

        System.out.println("Quitting application...");
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: handles input
    public void handleInputInMapOrNo() throws InvalidInputException {
        if (isInMap) {
            if (manageMapAction(cmdString)) {
                isInMap = false;
                cards.show(this.getContentPane(), "menuPanel");
                repaint();
            }
        } else {
            handleInput(cmdString);
        }
    }

    // EFFECTS: displays menu of options to user, with information on stored maps
    // and selected map
    private void displayMenu() {
        System.out.println("\nMAIN MENU");
        if (maps.isEmpty()) {
            System.out.println("No maps! Make a new one?");
        } else {
            printSelectInfo();
        }

        System.out.println("\nSelect from:");
        System.out.println("\tn -> New Map");
        System.out.println("\tl -> Load Saved Maps");
        if (canCycleSelect()) {
            System.out.println("\ta -> Previous Map Slot");
            System.out.println("\td -> Next Map Slot");
        }
        if (!maps.isEmpty()) {
            System.out.println("\tm -> Manage Map");
            System.out.println("\tf -> Map Info");
            System.out.println("\ts -> Save maps");
        }
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: calls various methods depending on input string
    @SuppressWarnings("methodlength")
    private void handleInput(String cmdString) throws InvalidInputException {
        switch (cmdString) {
            case "q":
                quit = true;
                break;
            case "n":
                System.out.print("Enter name: ");
                String newMapName = input.next();
                createMap(newMapName);
            case "m":
                manageMap(selectedMap);
                break;
            case "f":
                printMapInfo();
                break;
            case "s":
                saveMapsState();
                break;
            case "l":
                loadMapsState();
                break;
            case "a":
                cycleBackward();
                break;
            case "d":
                cycleForward();
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: adds a new map to list with specified name and selects it
    void createMap(String name) {
        CustomMap newMap = new CustomMap(name);
        maps.add(newMap);
        mapsListModel.addElement(newMap);
        selectMap(newMap);
        repaint();
    }

    // EFFECTS: prints info on stored maps and selected map
    private void printSelectInfo() {
        System.out.println();
        System.out.println("# of maps: " + maps.size());
        System.out.print("Selected map: [" + (selectIndex + 1) + "] ");
        System.out.println("\'" + selectedMap.getName() + "\'");
    }

    // EFFECTS: returns true if maps.size() > 1
    private boolean canCycleSelect() {
        return maps.size() > 1;
    }

    // REQUIRES: maps.size() > 1
    // MODIFIES: this
    // EFFECTS: cycles selected map either forwards or backwards, wraps the list if
    // at end
    private void cycleForward() throws InvalidInputException {
        if (!canCycleSelect()) {
            throw new InvalidInputException();
        }
        selectIndex++;
        selectIndex = selectIndex % maps.size();
        selectedMap = maps.get(selectIndex);
    }

    // MODIFIES, EFFECTS: see above
    private void cycleBackward() throws InvalidInputException {
        if (!canCycleSelect()) {
            throw new InvalidInputException();
        }
        selectIndex--;
        selectIndex += maps.size();
        selectIndex = selectIndex % maps.size();
        selectedMap = maps.get(selectIndex);
    }

    // MODIFIES: this
    // EFFECTS: handles operations on selected map object, if no map selected throws
    // expception
    void manageMap(CustomMap map) throws InvalidInputException {
        if (map == null) {
            throw new InvalidInputException();
        }

        isInMap = true;
        changeToMapPanel(map);

        manageMapConfirm(map);

        cards.show(this.getContentPane(), "menuPanel");
        repaint();
        isInMap = false;
    }

    // MODIFIES: this
    // EFFECTS: handles operations on selected map object, if no map selected throws
    // expception
    void manageMapConfirm(CustomMap map) throws InvalidInputException {
        boolean quit = false;

        while (!quit) {
            printMapInfo();
            System.out.println("Editing map.");
            printManageMenu();

            cmdString = takeInput();
            try {
                if (manageMapAction(cmdString)) {
                    quit = true;
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: couldn't interpret input.");
                quit = incrementSpam();
                continue;
            }
            spamCount = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method for manageMap and runMapMaker; increases spam count by
    // 1 and checks if it is >3
    private boolean incrementSpam() {
        spamCount++;
        return checkSpam();
    }

    // MODIFIES: this
    // EFFECTS: performs certain functionalities based on passed string, returns
    // true if quit and false otherwise
    private boolean manageMapAction(String cmdString) throws InvalidInputException {
        switch (cmdString) {
            case "n":
                constructFeature();
                break;
            case "e":
                editFeature();
                break;
            case "d":
                if (!deleteMap()) {
                    break;
                }
            case "q":
                return true;
            default:
                throw new InvalidInputException();
        }
        return false;
    }

    // EFFECTS: prints menu for managing map
    private void printManageMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tn -> New Feature!");
        System.out.println("\te -> Edit Feature");
        System.out.println("\td -> Delete Map.");
        System.out.println("\tq -> Back to menu!");
    }

    // REQUIRES: selectedMap != null
    // EFFECTS: print selected map info
    private void printMapInfo() throws InvalidInputException {
        if (selectedMap == null) {
            throw new InvalidInputException();
        }

        printMapInfoConfirm();
    }

    // REQUIRES: selectedMap != null
    // EFFECTS: print selected map info
    private void printMapInfoConfirm() {
        infoText.setText("");
        mapInfoText.setText("");
        List<String> mapInfo = selectedMap.mapInfo();
        for (String b : mapInfo) {
            System.out.println(b);
            infoText.append(b + "\n");
            mapInfoText.append(b + "\n");
        }
        // displayMenu();
    }

    // REQUIRES: selectedMap != null
    // MODIFIES: this
    // EFFECTS: deletes selected map
    private boolean deleteMap() throws InvalidInputException {
        System.out.println("!!!!! Are you sure? y/n");
        cmdString = takeInput();
        if (cmdString.equals("y")) {
            deleteMapConfirm();
            return true;
        } else if (cmdString.equals("n")) {
            System.out.println("Cancelled");
            return false;
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes selected map
    private void deleteMapConfirm() {
        System.out.println("WE ARE DELETING YOUR MAP...");
        maps.remove(selectIndex);
        selectIndex = 0;
        try {
            selectedMap = maps.get(selectIndex);
        } catch (IndexOutOfBoundsException e) {
            selectedMap = null;
        }
        updateListPanel();
    }

    // MODIFIES: this
    // EFFECTS: asks whether user wants to add a marker or object to the map, then
    // does that
    private void constructFeature() throws InvalidInputException {
        System.out.println("\tMarker (we haven't implemented marker yet): m");
        System.out.println("\tRoute: r");
        System.out.println("\tObject: o");

        String choice = takeInput();
        switch (choice) {
            case "m":
                System.out.println("Sorry, we haven't programmed this part yet!");
                break;
            case "r":
                constructRoute();
                break;
            case "o":
                constructObject();
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks which feature user wants to edit and does that
    private void editFeature() throws InvalidInputException {
        System.out.println("\tMarker (we haven't implemented marker yet): m");
        System.out.println("\tRoute: r");
        System.out.println("\tObject: o");

        String choice = takeInput();
        switch (choice) {
            case "m":
                System.out.println("Sorry, we haven't programmed this part yet!");
                break;
            case "r":
                editRoute();
                break;
            case "o":
                editObject();
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and then modifies selected route
    private void editRoute() throws InvalidInputException {
        if (selectedMap.getRoutes().isEmpty()) {
            System.out.println("No routes!");
            return;
        }
        System.out.println("Which route?");

        cmdString = takeInput();
        Route selectRoute = selectedMap.findRoute(cmdString);

        System.out.println("Delete? (y/n)");

        if (takeYesNo()) {
            selectedMap.deleteRoute(selectRoute);
            return;
        }

        try {
            System.out.print("name: ");
            String newName = input.next();

            Route newRoute = cons.constructRoute(newName, 0, 0, input);
            selectedMap.editRoute(selectRoute, newRoute);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and then modifies selected route
    private void editObject() throws InvalidInputException {
        if (selectedMap.getObjects().isEmpty()) {
            System.out.println("No objects!");
            return;
        }
        System.out.println("Which object?");

        cmdString = takeInput();
        MapObject selectObject = selectedMap.findObject(cmdString);

        try {
            handleEditObject(selectObject);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: prints a menu for when user is editing an object
    private void printEditObjectMenu() {
        System.out.println("Edit name (n)");
        System.out.println("Edit position (a)");
        System.out.println("Edit dimensions (s)");
        System.out.println("Edit height (f)");
        System.out.println("Delete (d)");
    }

    // MODIFIES: this
    // EFFECTS: asks user for input and will do different edits depending on input
    // to selectObject
    private void handleEditObject(MapObject selectObject) throws InvalidInputException, ObjectClassificationException {
        printEditObjectMenu();
        cmdString = takeInput();

        switch (cmdString) {
            case "n":
                System.out.println("name: ");
                cmdString = input.next();
                selectObject.setName(cmdString);
                break;
            case "a":
                editObjectCoords(selectObject);
                break;
            case "s":
                editObjectDimensions(selectObject);
                break;
            case "f":
                editObjectHeight(selectObject);
                break;
            case "d":
                selectedMap.deleteObject(selectObject);
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for input and edits selectObject
    private void editObjectCoords(MapObject selectObject)
            throws InvalidInputException {
        try {
            System.out.println("x: ");
            selectObject.setXpos(Integer.parseInt(input.next()));
            System.out.println("y: ");
            selectObject.setYpos(Integer.parseInt(input.next()));
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and appropriately changes passed object
    private void editObjectDimensions(MapObject selectObject)
            throws ObjectClassificationException, InvalidInputException {
        try {
            switch (selectObject.getType()) {
                case "building":
                    System.out.println("width (x): ");
                    selectObject.setXdim(Integer.parseInt(input.next()));
                    System.out.println("length (y): ");
                    selectObject.setYdim(Integer.parseInt(input.next()));
                    break;
                case "tree":
                    System.out.println("radius: ");
                    selectObject.setRadius(Integer.parseInt(input.next()));
                    break;
                default:
                    throw new ObjectClassificationException();
            }
        } catch (ObjectClassificationException e) {
            System.out.println("Couldn't find object type.");
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets object height to user input
    private void editObjectHeight(MapObject selectObject)
            throws InvalidInputException {
        try {
            System.out.println("height: ");
            selectObject.setHeight(Integer.parseInt(input.next()));
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for specifications from user, then creates a new object in
    // selected map
    private void constructObject() throws InvalidInputException {
        try {
            String type = takeTypeObject();

            System.out.print("x: ");
            int newObjX = input.nextInt();
            System.out.print("y: ");
            int newObjY = input.nextInt();
            System.out.print("name: ");
            String newObjName = input.next();
            System.out.println("height: ");
            int newObjHeight = input.nextInt();

            MapObject newObj = convertObjType(type, newObjX, newObjY, newObjName, newObjHeight);

            selectedMap.addObject(newObj);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for specifications from user, then creates a new route in
    // selected map
    private void constructRoute() throws InvalidInputException {
        try {
            System.out.print("name: ");
            String newName = input.next();

            selectedMap.addRoute(cons.constructRoute(newName, 0, 0, input));
            updateInfoPanel();
            repaint();
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: asks user for a string representing type of object and returns it
    private String takeTypeObject() throws InvalidInputException {
        System.out.println("Type of object: (\"buil\" or \"tree\")");
        String type = takeInput();
        if (!checkValidType(type)) {
            throw new InvalidInputException();
        }
        return type;
    }

    // EFFECTS: writes map information to file
    private void saveMapsState() throws InvalidInputException {
        if (maps.isEmpty()) {
            throw new InvalidInputException();
        }

        System.out.println("Are you sure you want to save the current maps to the file? (y/n)");
        if (!takeYesNo()) {
            System.out.println("Cancelled.");
            return;
        }

        saveMapsStateConfirm();

    }

    // EFFECTS: writes map information to file
    private void saveMapsStateConfirm() {
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_LOCATION);
            jsonWriter.open();
            jsonWriter.write(maps);
            jsonWriter.close();
            System.out.println("Saved maps to " + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_LOCATION);
        }
        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: loads map information from file
    private void loadMapsState() throws InvalidInputException {
        System.out.println("Are you sure you want to load the saved maps from file, replacing the current maps? (y/n)");
        if (!takeYesNo()) {
            System.out.println("Cancelled.");
            return;
        }

        loadMapsStateConfirm();
    }

    // MODIFIES: this
    // EFFECTS: loads map information from file
    private void loadMapsStateConfirm() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_LOCATION);
            maps = jsonReader.read();
            updateListPanel();
            selectIndex = 0;
            selectedMap = maps.get(selectIndex);
            selectMap(selectedMap);
            System.out.println("Loaded maps from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_LOCATION);
        } catch (ObjectClassificationException e) {
            System.out.println("Unable to determine object type");
        } catch (JSONException e) {
            try {
                JsonWriter jsonWriter = new JsonWriter(JSON_LOCATION);
                jsonWriter.open();
                jsonWriter.write(new ArrayList<>());
                jsonWriter.close();
            } catch (FileNotFoundException e2) {
                System.out.println("Unable to write to file: " + JSON_LOCATION);
            }
        }
        displayMenu();
    }

    // EFFECTS: returns a subclass of mapobject depending on string passed
    private MapObject convertObjType(String type, int newObjX, int newObjY, String newObjName, int newObjHeight)
            throws InvalidInputException {
        MapObject newObj;

        switch (type) {
            case CustomMap.objectCodeBuilding:
                newObj = cons.constructBuilding(newObjName, newObjX, newObjY, newObjHeight, input);
                break;
            case CustomMap.objectCodeTree:
                newObj = cons.constructTree(newObjName, newObjX, newObjY, input);
                break;
            default:
                throw new InvalidInputException();
        }

        return newObj;
    }

    // EFFECTS: returns false if type not a valid type of object
    private boolean checkValidType(String type) {
        switch (type) {
            case CustomMap.objectCodeBuilding:
                break;
            case CustomMap.objectCodeTree:
                break;
            default:
                return false;
        }
        return true;
    }

    // REQUIRES: map != null
    // MODIFIES: this
    // EFFECTS: selects map
    void selectMap(CustomMap map) {
        selectedMap = map;
        selectIndex = maps.indexOf(map);
        mapsJList.setSelectedIndex(selectIndex);
        mapsJList.ensureIndexIsVisible(selectIndex);
    }

    // EFFECTS: returns a lowercase string of the next user input
    String takeInput() {
        String str = input.next();
        str = str.toLowerCase();
        return str;
    }

    // EFFECTS: returns true if user has entered a wrong input 3 times in a row in a
    // looping menu
    boolean checkSpam() {
        return spamCount >= 3;
    }

    // EFFECTS: returns true if input is y, false if input is n, else throws
    // exception
    boolean takeYesNo() throws InvalidInputException {
        cmdString = takeInput();
        switch (cmdString) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                throw new InvalidInputException();
        }
    }
}
