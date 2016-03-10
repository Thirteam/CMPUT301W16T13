package cmput301.textbookhub.Models;

import android.os.Bundle;

/**
 * Created by Fred on 2016/3/10.
 */
public interface DataBundleObject extends UniqueItem<String>{

    DataBundleLabel getDataModelLabel();

    Bundle generateDataBundle();

}
