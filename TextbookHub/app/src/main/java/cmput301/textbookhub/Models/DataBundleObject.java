package cmput301.textbookhub.Models;

import android.os.Bundle;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public interface DataBundleObject extends UniqueItem<String>{

    DataBundleLabel getDataModelLabel();

    Bundle generateDataBundle();

}
