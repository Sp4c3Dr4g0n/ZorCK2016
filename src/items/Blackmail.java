package items;

import core.*;

/**
 *
 * @author dayley3391b
 */
public class BlackMail extends Item {

    public BlackMail() {
        super();
        this.usage().take(Item.TAKABLE);
        this.name("BlackMail")
                .description("It's a note from an unknown author...")
                .synonym("mail");
    }
    @Override
    public void interact(final Command command, final Context context){

    }
}