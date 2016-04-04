package cmput301.textbookhub.Controllers;

import cmput301.textbookhub.Views.BaseView;

/**
 * <code>ActivityControllerFactory</code> producing controllers for different activities
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/10
 * @see ActivityController
 *
 * Created by Fred on 2016/3/10.
 */
public class ActivityControllerFactory {

    public enum FactoryCatalog{
        ACTIVITY_EDIT_BOOK,
        ACTIVITY_LOGIN,
        ACTIVITY_MAIN,
        ACTIVITY_MY_BIDS,
        ACTIVITY_MY_BORROWS,
        ACTIVITY_MY_INVENTORY,
        ACTIVITY_USER_PROFILE,
        ACTIVITY_VIEW_BOOK,
        ACTIVITY_MAP
    }

    public static ActivityController getControllerForView(FactoryCatalog catalog, BaseView v){
        ActivityController controller;
        switch(catalog){
            case ACTIVITY_EDIT_BOOK:
                controller = new EditBookActivityController();
                break;
            case ACTIVITY_LOGIN:
                controller = new LoginActivityController();
                break;
            case ACTIVITY_MAIN:
                controller = new MainActivityController();
                break;
            case ACTIVITY_MY_BIDS:
                controller = new MyBidsActivityController();
                break;
            case ACTIVITY_MY_BORROWS:
                controller = new MyBorrowsActivityController();
                break;
            case ACTIVITY_MY_INVENTORY:
                controller = new MyInventoryActivityController();
                break;
            case ACTIVITY_USER_PROFILE:
                controller = new UserProfileActivityController();
                break;
            case ACTIVITY_VIEW_BOOK:
                controller = new ViewBookActivityController();
                break;
            case ACTIVITY_MAP:
                controller = new MapActivityController();
                break;
            default:
                controller = new ActivityController(){};
                break;
        }
        controller.addBaseView(v);
        return controller;
    }

}
