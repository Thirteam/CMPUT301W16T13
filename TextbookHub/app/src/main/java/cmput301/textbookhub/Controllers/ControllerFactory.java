package cmput301.textbookhub.Controllers;

import cmput301.textbookhub.Views.BaseView;

/**
 * Created by Fred on 2016/3/10.
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

    public static BaseController getControllerForView(FactoryCatalog catalog, BaseView v){
        BaseController controller;
        switch(catalog){
            case ACTIVITY_EDIT_BOOK:
                controller = EditBookActivityController.getInstance();
                break;
            case ACTIVITY_LOGIN:
                controller = LoginActivityController.getInstance();
                break;
            case ACTIVITY_MAIN:
                controller = MainActivityController.getInstance();
                break;
            case ACTIVITY_MY_BIDS:
                controller = MyBidsActivityController.getInstance();
                break;
            case ACTIVITY_MY_BORROWS:
                controller = MyBorrowsActivityController.getInstance();
                break;
            case ACTIVITY_MY_INVENTORY:
                controller = MyInventoryActivityController.getInstance();
                break;
            case ACTIVITY_USER_PROFILE:
                controller = UserProfileActivityController.getInstance();
                break;
            case ACTIVITY_VIEW_BOOK:
                controller = ViewBookActivityController.getInstance();
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
