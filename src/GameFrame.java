import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameFrame extends JPanel implements ActionListener, MouseListener, KeyListener, MouseMotionListener{

    static int width = 1080;
    static int height = 720;
    private int mouseLeftPos;
    private int mouseTopPos;
    private boolean debug = false;

    private int foodResourceCounter = 0;
    private int woodResourceCounter = 0;
    private int stoneResourceCounter = 0;
    private int steelResourceCounter = 0;
    private int jNationCitizenCounter = 0;
    private int houseCounter = 0;
    private Rectangle createCitizenButton;
    private Rectangle createHouseButton;
    private boolean showContextMenu = false;

    private int citizenPrice = 50;
    private int housingPriceWood = 50;
    private int housingPriceStone = 25;




    private boolean showCreateHouseContextMenu = false;
    private int contextMenuHeight = 5; // Adjust this height to fit your menu


    Font myFont = new Font("Serif", Font.BOLD, 20);

    // Resources
    FoodResource food = new FoodResource(width / 8, height / 8);
    WoodResource wood = new WoodResource(width / 2 + 10, height / 8);
    StoneResource stone = new StoneResource(width / 4 + 75, height / 8);
    SteelResource steel = new SteelResource(775, height / 8);


    FoodResource foodIcon = new FoodResource(3,0,162,122,0.4,0.4,1);
    WoodResource woodIcon = new WoodResource(3,18,162,122,0.4,0.4,1);
    StoneResource stoneIcon = new StoneResource(3,40,162,122,0.4,0.4,1);
    SteelResource steelIcon = new SteelResource(3,60,162,122,0.4,0.4,1);


    FoodResource foodIcon2 = new FoodResource(170,640,162,122,0.4,0.4,1);
    WoodResource woodIcon2 = new WoodResource(570,620,162,122,0.4,0.4,1);
    StoneResource stoneIcon2 = new StoneResource(570,645,162,122,0.4,0.4,1);
    SteelResource steelIcon2 = new SteelResource(3,60,162,122,0.4,0.4,1);




    // Citizens
    jnationCitizen citizenIcon = new jnationCitizen(50, 660);
    Housing houseIcon = new Housing(460,660);
    ArrayList<jnationCitizen> citizens = new ArrayList<jnationCitizen>();

    public GameFrame() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setLayout(null); // Use null layout for absolute positioning
        addMouseMotionListener(this); // Add MouseMotionListener to detect hover
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);

        Timer t = new Timer(16, this);
        t.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        foodIcon.paint(g);
        steelIcon.paint(g);
        stoneIcon.paint(g);
        woodIcon.paint(g);
        // Display counters
        g.setFont(myFont);
        g.setColor(Color.BLACK);
        g.drawString("     food available: " + foodResourceCounter, 10, 20);
        g.drawString("     wood available: " + woodResourceCounter, 10, 40);
        g.drawString("     stone available: " + stoneResourceCounter, 10, 60);
        g.drawString("     steel available: " + steelResourceCounter, 10, 80);

        g.setColor(Color.GRAY);
        g.drawString("j nation citizens: " + jNationCitizenCounter, 55, 710);
        g.drawString("housing available: " + houseCounter, 460, 710);
        g.setColor(Color.BLACK);

        // Draw resources
        food.paint(g);
        wood.paint(g);
        stone.paint(g);
        steel.paint(g);



        // Draw citizen purchases
        citizenIcon.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // Check if there is enough food and set color accordingly
        if (foodResourceCounter >= citizenPrice && houseCounter >= 1) {
            g2d.setColor(Color.BLACK);  // Normal color if enough food
        } else {
            g2d.setColor(Color.GRAY);   // Grey color if not enough food
            // Optional: Reduce alpha for more subtle grey effect (only on text)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% opacity
        }

        // Draw the "create citizen" text
        g2d.drawString("create citizen", 90, 680);


        // Reset composite to fully opaque after drawing the text
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Show context menu when hovering over "create citizen"
        if (showContextMenu) {
            g.setColor(Color.WHITE);
            g.fillRect(90, 645, 180, 5); // Draw background for context menu

            g.setColor(Color.BLACK);
            String priceText = "Cost: " + citizenPrice + " food";
            g.drawString(priceText, 100, 660); // Show the cost

            // Measure the width of the price text
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int priceTextWidth = metrics.stringWidth(priceText);

            // Adjust the food icon's position based on the text width
            int foodIconX = 100 + priceTextWidth; // Add padding after the text
            foodIcon2.setPost(foodIconX, 640); // Update the icon's position dynamically
            foodIcon2.paint(g); // Draw the food icon
        }


        // Draw housing purchases
        houseIcon.paint(g);
        // Check if there is enough resources and set color accordingly
        if (woodResourceCounter >= housingPriceWood && stoneResourceCounter >= housingPriceStone) {
            g2d.setColor(Color.BLACK);  // Normal color if enough resources
        } else {
            g2d.setColor(Color.GRAY);   // Grey color if not enough resources
            // Optional: Reduce alpha for more subtle grey effect (only on text)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% opacity
        }

        // Draw the "create citizen" text
        g2d.drawString("create housing", 500, 680);


        // Reset composite to fully opaque after drawing the text
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));


        // Draw the "create housing" context menu if needed
        if (showCreateHouseContextMenu) {
            g.setColor(Color.WHITE);
            g.fillRect(510, 645, 150, 5); // Draw background for context menu

            g.setColor(Color.BLACK);
            String woodPriceText = "Cost: "+housingPriceWood+" wood";
            String stonePriceText = "          "+housingPriceStone+" stone";

            // Draw wood cost
            g.drawString(woodPriceText, 520, 640);

            // Measure the width of the wood cost text
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int woodPriceTextWidth = metrics.stringWidth(woodPriceText);

            // Adjust the wood icon's position based on the text width
            int woodIconX = 520 + woodPriceTextWidth; // Add padding after the text
            woodIcon2.setPost(woodIconX, 615); // Update the icon's position dynamically
            woodIcon2.paint(g);

            // Draw stone cost
            g.drawString(stonePriceText, 520, 660);

            // Measure the width of the stone cost text
            int stonePriceTextWidth = metrics.stringWidth(stonePriceText);

            // Adjust the stone icon's position based on the text width
            int stoneIconX = 520 + stonePriceTextWidth; // Add padding after the text
            stoneIcon2.setPost(stoneIconX, 640); // Update the icon's position dynamically
            stoneIcon2.paint(g);
        }


        // Define the clickable area around the create citizen string
        FontMetrics metrics = g.getFontMetrics(myFont);
        int textWidth = metrics.stringWidth("create citizen");
        int textHeight = metrics.getHeight();
        createCitizenButton = new Rectangle(90, 680 - textHeight, textWidth, textHeight);
        textWidth = metrics.stringWidth("create housing");
        textHeight = metrics.getHeight();
        createHouseButton = new Rectangle(500,680 - textHeight, textWidth, textHeight);




        // Draw citizens
        for (jnationCitizen citizen : citizens) {
            citizen.paint(g);
        }

        // Debug overlays
        if (debug) {
            food.drawHitBox(g);
            wood.drawHitBox(g);
            stone.drawHitBox(g);
            steel.drawHitBox(g);
            g.drawRect(mouseLeftPos, mouseTopPos, 2, 2);
            g.drawRect(createCitizenButton.x, createCitizenButton.y, createCitizenButton.width, createCitizenButton.height);
            g.drawRect(createHouseButton.x, createHouseButton.y, createHouseButton.width, createHouseButton.height);
            g.drawString("DEBUG MODE", 900, 20);
        }


    }

    public static void main(String[] args) {
        JFrame f = new JFrame("J Nation");
        GameFrame game = new GameFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.add(game, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null); // Center window
        f.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseLeftPos = e.getX();
        mouseTopPos = e.getY();

        // Increment counters for resource clicks
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (food.isCursorInside(e.getX(), e.getY())) foodResourceCounter++;
            if (wood.isCursorInside(e.getX(), e.getY())) woodResourceCounter++;
            if (stone.isCursorInside(e.getX(), e.getY())) stoneResourceCounter++;
            if (steel.isCursorInside(e.getX(), e.getY())) steelResourceCounter++;

            // Check if the click is inside the "create citizen" rectangle
            if (createCitizenButton != null && createCitizenButton.contains(mouseLeftPos, mouseTopPos)) {
                if (foodResourceCounter >= citizenPrice && houseCounter >= 1) {
                    System.out.println("Create Citizen Button Clicked!");
                    // Add logic to create a citizen
                    citizens.add(new jnationCitizen(100, 100));
                    jNationCitizenCounter++;
                    citizenPrice *= 1.15;
                    foodResourceCounter -= citizenPrice;
                    houseCounter -= 1;
                }
            }


            // Check if the click is inside the "creating housing" rectangle
            if (createHouseButton != null && createHouseButton.contains(mouseLeftPos, mouseTopPos)) {
                if (woodResourceCounter >= housingPriceWood && stoneResourceCounter >= housingPriceStone) {
                    System.out.println("Create Housing Button Clicked!");
                    houseCounter++;
                    housingPriceStone *= 1.15;
                    housingPriceWood *= 1.15;
                    woodResourceCounter -= housingPriceWood;
                    stoneResourceCounter -= housingPriceStone;
                }
            }

            if (debug) {
                System.out.println("Mouse X: " + e.getX() + " Mouse Y: " + e.getY());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            debug = !debug; // Toggle debug mode
        }
        if (e.getKeyCode() == KeyEvent.VK_F2 && debug) {
            foodResourceCounter += 10000;
            woodResourceCounter += 10000;
            stoneResourceCounter += 10000;
            steelResourceCounter += 10000;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Detect when the mouse hovers over the "create citizen" area

        if (createCitizenButton != null && createCitizenButton.contains(e.getX(), e.getY())) {
            showContextMenu = true;  // Show context menu
        } else {
            showContextMenu = false; //
        }

        // Check if hovering over the "create housing" string
        if (createHouseButton != null && createHouseButton.contains(e.getX(), e.getY())) {
            showCreateHouseContextMenu = true;
        } else {
            showCreateHouseContextMenu = false;
        }

        repaint();  // Trigger a repaint to show or hide the context menus




    }



    // Unused interface methods
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        // Empty implementation (if you don't want to handle dragging)
    }
}
