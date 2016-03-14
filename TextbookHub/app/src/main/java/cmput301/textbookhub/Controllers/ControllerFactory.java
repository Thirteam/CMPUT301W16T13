package cmput301.textbookhub.Controllers;

import cmput301.textbookhub.Views.BaseView;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class ControllerFactory {

    private static ControllerFactory instance = null;

    public enum FactoryCatalog{
        ACTIVITY_EDIT_BOOK,
        ACTIVITY_LOGIN,
        ACTIVITY_MAIN,
        ACTIVITY_MY_BIDS,
        ACTIVITY_MY_BORROWS,
        ACTIVITY_MY_INVENTORY,
        ACTIVITY_USER_PROFILE,
        ACTIVITY_VIEW_BOOK
    }

    public static BaseController getControllerForView(FactoryCatalog catalog, BaseView v, String username){
        BaseController controller;
        switch(catalog){
            case ACTIVITY_EDIT_BOOK:
                controller = EditBookActivityController.getInstance(username);
                break;
            case ACTIVITY_LOGIN:
                controller = LoginActivityController.getInstance();
                break;
            case ACTIVITY_MAIN:
                controller = MainActivityController.getInstance(username);
                break;
            case ACTIVITY_MY_BIDS:
                controller = MyBidsActivityController.getInstance(username);
                break;
            case ACTIVITY_MY_BORROWS:
                controller = MyBorrowsActivityController.getInstance(username);
                break;
            case ACTIVITY_MY_INVENTORY:
                controller = MyInventoryActivityController.getInstance(username);
                break;
            case ACTIVITY_USER_PROFILE:
                controller = UserProfileActivityController.getInstance(username);
                break;
            case ACTIVITY_VIEW_BOOK:
                controller = ViewBookActivityController.getInstance(username);
                break;
            default:
                controller = new BaseController(){};
                break;
        }
        controller.addBaseView(v);
        return controller;
    }

    public static ControllerFactory getInstance(){
        if(instance == null)
            instance = new ControllerFactory();
        return instance;
    }
}
