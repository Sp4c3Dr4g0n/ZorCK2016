
package items;

import core.*;

/**
 *
 * @author dayley3391b
 */
public class IncriminatingEvidence extends Item {
    
    public IncriminatingEvidence(){
        super();
        this.usage().take(Item.TAKABLE);
        this.name("Incriminating Evidence")
                .description("It's evidence of Mr.Booth...")
                .synonym("Evidence","Criminal","IE");
    }
    @Override
    public void interact(final Command command, final Context context){

    }
}
