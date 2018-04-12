import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('OrderItem e2e test', () => {

    let navBarPage: NavBarPage;
    let orderItemDialogPage: OrderItemDialogPage;
    let orderItemComponentsPage: OrderItemComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OrderItems', () => {
        navBarPage.goToEntity('order-item');
        orderItemComponentsPage = new OrderItemComponentsPage();
        expect(orderItemComponentsPage.getTitle())
            .toMatch(/manleApp.orderItem.home.title/);

    });

    it('should load create OrderItem dialog', () => {
        orderItemComponentsPage.clickOnCreateButton();
        orderItemDialogPage = new OrderItemDialogPage();
        expect(orderItemDialogPage.getModalTitle())
            .toMatch(/manleApp.orderItem.home.createOrEditLabel/);
        orderItemDialogPage.close();
    });

    it('should create and save OrderItems', () => {
        orderItemComponentsPage.clickOnCreateButton();
        orderItemDialogPage.setProductNameInput('productName');
        expect(orderItemDialogPage.getProductNameInput()).toMatch('productName');
        orderItemDialogPage.setProductPriceInput('5');
        expect(orderItemDialogPage.getProductPriceInput()).toMatch('5');
        orderItemDialogPage.setProductQuantityInput('5');
        expect(orderItemDialogPage.getProductQuantityInput()).toMatch('5');
        orderItemDialogPage.setProductIconInput('productIcon');
        expect(orderItemDialogPage.getProductIconInput()).toMatch('productIcon');
        orderItemDialogPage.orderMasterSelectLastOption();
        orderItemDialogPage.productSelectLastOption();
        orderItemDialogPage.save();
        expect(orderItemDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrderItemComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-order-item div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OrderItemDialogPage {
    modalTitle = element(by.css('h4#myOrderItemLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productNameInput = element(by.css('input#field_productName'));
    productPriceInput = element(by.css('input#field_productPrice'));
    productQuantityInput = element(by.css('input#field_productQuantity'));
    productIconInput = element(by.css('input#field_productIcon'));
    orderMasterSelect = element(by.css('select#field_orderMaster'));
    productSelect = element(by.css('select#field_product'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setProductNameInput = function(productName) {
        this.productNameInput.sendKeys(productName);
    };

    getProductNameInput = function() {
        return this.productNameInput.getAttribute('value');
    };

    setProductPriceInput = function(productPrice) {
        this.productPriceInput.sendKeys(productPrice);
    };

    getProductPriceInput = function() {
        return this.productPriceInput.getAttribute('value');
    };

    setProductQuantityInput = function(productQuantity) {
        this.productQuantityInput.sendKeys(productQuantity);
    };

    getProductQuantityInput = function() {
        return this.productQuantityInput.getAttribute('value');
    };

    setProductIconInput = function(productIcon) {
        this.productIconInput.sendKeys(productIcon);
    };

    getProductIconInput = function() {
        return this.productIconInput.getAttribute('value');
    };

    orderMasterSelectLastOption = function() {
        this.orderMasterSelect.all(by.tagName('option')).last().click();
    };

    orderMasterSelectOption = function(option) {
        this.orderMasterSelect.sendKeys(option);
    };

    getOrderMasterSelect = function() {
        return this.orderMasterSelect;
    };

    getOrderMasterSelectedOption = function() {
        return this.orderMasterSelect.element(by.css('option:checked')).getText();
    };

    productSelectLastOption = function() {
        this.productSelect.all(by.tagName('option')).last().click();
    };

    productSelectOption = function(option) {
        this.productSelect.sendKeys(option);
    };

    getProductSelect = function() {
        return this.productSelect;
    };

    getProductSelectedOption = function() {
        return this.productSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
