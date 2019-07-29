import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Nforcer", info = "My new script", logo = "", name = "My new script", version = 0)
public class FirstScript extends Script
{

    boolean gotWood = false;
    boolean fishingDone = false;

    @Override
    public void onStart() throws InterruptedException
    {
        log("Script started.");
    }

    // this is the main
    @Override
    public int onLoop() throws InterruptedException
    {

        // Check if player is animating at all
        if (!myPlayer().isAnimating())
        {
            // Check if fishing cycle is done
            if (!fishingDone)
            {
                // Find the fishing net
                if (getInventory().contains(303))
                {
                    log("The script found the fishing net.\n");

                    // check if inventory is empty
                    if (getInventory().getEmptySlotCount() > 0)
                    {
                        log("Finding Fishing spot.\n");
                        NPC fishingSpot = getNpcs().closest("Fishing spot");
                        log("Has it found the fishing spot: "+fishingSpot.isVisible());
                        log("Does the fishing spot exist: "+fishingSpot.exists());
                        //myPosition().distance(fishingSpot);

                        if (!fishingSpot.isVisible())
                        {
                            // Walk to the fishing spot
                            log("Player is walking to fishing spot.\n");
                            getWalking().walk(fishingSpot);
                        }
                        else if (fishingSpot.isVisible())
                        {
                            // Start fishing
                            log("Player is now fishing.\n");
                            fishingSpot.interact("Net");
                        }
                    } else if (getInventory().isFull())
                    {
                        log("Inventory is full.\n");
                        log("Dropping a shrimp\n");
                        getInventory().drop("Raw shrimps");
                        fishingDone = true;
                    }
                } else
                {
                    log("The script didn't find the fishing net.\n");
                }

            } else
            {
                if (getInventory().contains("Logs"))
                {
                    // Start Fire
                    log("Starting fire.\n");
                    getInventory().interact("Use", "TinderBox");
                    getInventory().interact("Use", "Logs");
                    gotWood = true;
                }

                RS2Object tree = getObjects().closest("Dead tree");

                if (!gotWood)
                {
                    // if tree can't be found
                    log("Finding dead tree.\n");
                    if (!tree.isVisible())
                    {
                        // Walk to the tree
                        log("Walking to dead tree.\n");
                        getWalking().walk(tree);
                    }
                    else if (tree.isVisible())
                    {
                        // Start chopping tree
                        log("Chopping dead tree.\n");
                        tree.interact("Chop down");
                    }
                }
                else if (gotWood)
                {
                    if (getInventory().getEmptySlotCount() > 0)
                    {
                        //getGroundItems().getAll().contains()

//                        for (GroundItem item : getGroundItems().getAll())
//                        {
//                            if (item != null)
//                            {
//                                log(item.getName());
//                            }
//                        }

//                        if (getGroundItems().closest("Fire").isVisible())
//                        {
//                            gotWood = false;
//                        }
//                        else
//                        {
                        log("Attempting to cook shrimps.\n");
                        getInventory().interact("Use", "Raw shrimps");
                        objects.closest("Fire").interact("Use");
                        getKeyboard().pressKey(32);
//                        }
                    }
                }
            }
        }

        return 5000;
    }

    @Override
    public void onExit() throws InterruptedException
    {
        log("Script stopped.");
    }
}
