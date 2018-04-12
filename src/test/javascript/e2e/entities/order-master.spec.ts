import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('OrderMaster e2e test', () => {

    let navBarPage: NavBarPage;
    let orderMasterDialogPage: OrderMasterDialogPage;
    let orderMasterComponentsPage: OrderMasterComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OrderMasters', () => {
        navBarPage.goToEntity('order-master');
        orderMasterComponentsPage = new OrderMasterComponentsPage();
        expect(orderMasterComponentsPage.getTitle())
            .toMatch(/manleApp.orderMaster.home.title/);

    });

    it('should load create OrderMaster dialog', () => {
        orderMasterComponentsPage.clickOnCreateButton();
        orderMasterDialogPage = new OrderMasterDialogPage();
        expect(orderMasterDialogPage.getModalTitle())
            .toMatch(/manleApp.orderMaster.home.createOrEditLabel/);
        orderMasterDialogPage.close();
    });

    it('should create and save OrderMasters', () => {
        orderMasterComponentsPage.clickOnCreateButton();
        orderMasterDialogPage.setOrderNumberInput('orderNumber');
        expect(orderMasterDialogPage.getOrderNumberInput()).toMatch('orderNumber');
        orderMasterDialogPage.setTotalPricesInput('5');
        expect(orderMasterDialogPage.getTotalPricesInput()).toMatch('5');
        orderMasterDialogPage.setTotalQuanityInput('5');
        expect(orderMasterDialogPage.getTotalQuanityInput()).toMatch('5');
        orderMasterDialogPage.orderStatusSelectLastOption();
        orderMasterDialogPage.addressSelectLastOption();
        orderMasterDialogPage.clientUserSelectLastOption();
        orderMasterDialogPage.save();
        expect(orderMasterDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrderMasterComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-order-master div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OrderMasterDialogPage {
    modalTitle = element(by.css('h4#myOrderMasterLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    orderNumberInput = element(by.css('input#field_orderNumber'));
    totalPricesInput = element(by.css('input#field_totalPrices'));
    totalQuanityInput = element(by.css('input#field_totalQuanity'));
    orderStatusSelect = element(by.css('select#field_orderStatus'));
    addressSelect = element(by.css('select#field_address'));
    clientUserSelect = element(by.css('select#field_clientUser'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setOrderNumberInput = function(orderNumber) {
        this.orderNumberInput.sendKeys(orderNumber);
    };

    getOrderNumberInput = function() {
        return this.orderNumberInput.getAttribute('value');
    };

    setTotalPricesInput = function(totalPrices) {
        this.totalPricesInput.sendKeys(totalPrices);
    };

    getTotalPricesInput = function() {
        return this.totalPricesInput.getAttribute('value');
    };

    setTotalQuanityInput = function(totalQuanity) {
        this.totalQuanityInput.sendKeys(totalQuanity);
    };

    getTotalQuanityInput = function() {
        return this.totalQuanityInput.getAttribute('value');
    };

    setOrderStatusSelect = function(orderStatus) {
        this.orderStatusSelect.sendKeys(orderStatus);
    };

    getOrderStatusSelect = function() {
        return this.orderStatusSelect.element(by.css('option:checked')).getText();
    };

    orderStatusSelectLastOption = function() {
        this.orderStatusSelect.all(by.tagName('option')).last().click();
    };
    addressSelectLastOption = function() {
        this.addressSelect.all(by.tagName('option')).last().click();
    };

    addressSelectOption = function(option) {
        this.addressSelect.sendKeys(option);
    };

    getAddressSelect = function() {
        return this.addressSelect;
    };

    getAddressSelectedOption = function() {
        return this.addressSelect.element(by.css('option:checked')).getText();
    };

    clientUserSelectLastOption = function() {
        this.clientUserSelect.all(by.tagName('option')).last().click();
    };

    clientUserSelectOption = function(option) {
        this.clientUserSelect.sendKeys(option);
    };

    getClientUserSelect = function() {
        return this.clientUserSelect;
    };

    getClientUserSelectedOption = function() {
        return this.clientUserSelect.element(by.css('option:checked')).getText();
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
