import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ClientUser e2e test', () => {

    let navBarPage: NavBarPage;
    let clientUserDialogPage: ClientUserDialogPage;
    let clientUserComponentsPage: ClientUserComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ClientUsers', () => {
        navBarPage.goToEntity('client-user');
        clientUserComponentsPage = new ClientUserComponentsPage();
        expect(clientUserComponentsPage.getTitle())
            .toMatch(/manleApp.clientUser.home.title/);

    });

    it('should load create ClientUser dialog', () => {
        clientUserComponentsPage.clickOnCreateButton();
        clientUserDialogPage = new ClientUserDialogPage();
        expect(clientUserDialogPage.getModalTitle())
            .toMatch(/manleApp.clientUser.home.createOrEditLabel/);
        clientUserDialogPage.close();
    });

    it('should create and save ClientUsers', () => {
        clientUserComponentsPage.clickOnCreateButton();
        clientUserDialogPage.setUserNameInput('userName');
        expect(clientUserDialogPage.getUserNameInput()).toMatch('userName');
        clientUserDialogPage.setPhoneInput('phone');
        expect(clientUserDialogPage.getPhoneInput()).toMatch('phone');
        clientUserDialogPage.setPasswordInput('password');
        expect(clientUserDialogPage.getPasswordInput()).toMatch('password');
        clientUserDialogPage.setFigureInput('figure');
        expect(clientUserDialogPage.getFigureInput()).toMatch('figure');
        clientUserDialogPage.setPointInput('5');
        expect(clientUserDialogPage.getPointInput()).toMatch('5');
        // clientUserDialogPage.productSelectLastOption();
        clientUserDialogPage.save();
        expect(clientUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ClientUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-client-user div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ClientUserDialogPage {
    modalTitle = element(by.css('h4#myClientUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userNameInput = element(by.css('input#field_userName'));
    phoneInput = element(by.css('input#field_phone'));
    passwordInput = element(by.css('input#field_password'));
    figureInput = element(by.css('input#field_figure'));
    pointInput = element(by.css('input#field_point'));
    productSelect = element(by.css('select#field_product'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserNameInput = function(userName) {
        this.userNameInput.sendKeys(userName);
    };

    getUserNameInput = function() {
        return this.userNameInput.getAttribute('value');
    };

    setPhoneInput = function(phone) {
        this.phoneInput.sendKeys(phone);
    };

    getPhoneInput = function() {
        return this.phoneInput.getAttribute('value');
    };

    setPasswordInput = function(password) {
        this.passwordInput.sendKeys(password);
    };

    getPasswordInput = function() {
        return this.passwordInput.getAttribute('value');
    };

    setFigureInput = function(figure) {
        this.figureInput.sendKeys(figure);
    };

    getFigureInput = function() {
        return this.figureInput.getAttribute('value');
    };

    setPointInput = function(point) {
        this.pointInput.sendKeys(point);
    };

    getPointInput = function() {
        return this.pointInput.getAttribute('value');
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
