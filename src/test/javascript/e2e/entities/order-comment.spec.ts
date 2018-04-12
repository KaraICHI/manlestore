import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('OrderComment e2e test', () => {

    let navBarPage: NavBarPage;
    let orderCommentDialogPage: OrderCommentDialogPage;
    let orderCommentComponentsPage: OrderCommentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OrderComments', () => {
        navBarPage.goToEntity('order-comment');
        orderCommentComponentsPage = new OrderCommentComponentsPage();
        expect(orderCommentComponentsPage.getTitle())
            .toMatch(/manleApp.orderComment.home.title/);

    });

    it('should load create OrderComment dialog', () => {
        orderCommentComponentsPage.clickOnCreateButton();
        orderCommentDialogPage = new OrderCommentDialogPage();
        expect(orderCommentDialogPage.getModalTitle())
            .toMatch(/manleApp.orderComment.home.createOrEditLabel/);
        orderCommentDialogPage.close();
    });

    it('should create and save OrderComments', () => {
        orderCommentComponentsPage.clickOnCreateButton();
        orderCommentDialogPage.setLevelInput('5');
        expect(orderCommentDialogPage.getLevelInput()).toMatch('5');
        orderCommentDialogPage.setContentInput('content');
        expect(orderCommentDialogPage.getContentInput()).toMatch('content');
        orderCommentDialogPage.setCreatDateInput('2000-12-31');
        expect(orderCommentDialogPage.getCreatDateInput()).toMatch('2000-12-31');
        orderCommentDialogPage.orderItemSelectLastOption();
        orderCommentDialogPage.clientUserSelectLastOption();
        orderCommentDialogPage.save();
        expect(orderCommentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrderCommentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-order-comment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OrderCommentDialogPage {
    modalTitle = element(by.css('h4#myOrderCommentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    levelInput = element(by.css('input#field_level'));
    contentInput = element(by.css('input#field_content'));
    creatDateInput = element(by.css('input#field_creatDate'));
    orderItemSelect = element(by.css('select#field_orderItem'));
    clientUserSelect = element(by.css('select#field_clientUser'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLevelInput = function(level) {
        this.levelInput.sendKeys(level);
    };

    getLevelInput = function() {
        return this.levelInput.getAttribute('value');
    };

    setContentInput = function(content) {
        this.contentInput.sendKeys(content);
    };

    getContentInput = function() {
        return this.contentInput.getAttribute('value');
    };

    setCreatDateInput = function(creatDate) {
        this.creatDateInput.sendKeys(creatDate);
    };

    getCreatDateInput = function() {
        return this.creatDateInput.getAttribute('value');
    };

    orderItemSelectLastOption = function() {
        this.orderItemSelect.all(by.tagName('option')).last().click();
    };

    orderItemSelectOption = function(option) {
        this.orderItemSelect.sendKeys(option);
    };

    getOrderItemSelect = function() {
        return this.orderItemSelect;
    };

    getOrderItemSelectedOption = function() {
        return this.orderItemSelect.element(by.css('option:checked')).getText();
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
