import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ArticalComment e2e test', () => {

    let navBarPage: NavBarPage;
    let articalCommentDialogPage: ArticalCommentDialogPage;
    let articalCommentComponentsPage: ArticalCommentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ArticalComments', () => {
        navBarPage.goToEntity('artical-comment');
        articalCommentComponentsPage = new ArticalCommentComponentsPage();
        expect(articalCommentComponentsPage.getTitle())
            .toMatch(/manleApp.articalComment.home.title/);

    });

    it('should load create ArticalComment dialog', () => {
        articalCommentComponentsPage.clickOnCreateButton();
        articalCommentDialogPage = new ArticalCommentDialogPage();
        expect(articalCommentDialogPage.getModalTitle())
            .toMatch(/manleApp.articalComment.home.createOrEditLabel/);
        articalCommentDialogPage.close();
    });

    it('should create and save ArticalComments', () => {
        articalCommentComponentsPage.clickOnCreateButton();
        articalCommentDialogPage.setContentInput('content');
        expect(articalCommentDialogPage.getContentInput()).toMatch('content');
        articalCommentDialogPage.articalSelectLastOption();
        articalCommentDialogPage.clientUserSelectLastOption();
        articalCommentDialogPage.save();
        expect(articalCommentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ArticalCommentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-artical-comment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ArticalCommentDialogPage {
    modalTitle = element(by.css('h4#myArticalCommentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    contentInput = element(by.css('input#field_content'));
    articalSelect = element(by.css('select#field_artical'));
    clientUserSelect = element(by.css('select#field_clientUser'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setContentInput = function(content) {
        this.contentInput.sendKeys(content);
    };

    getContentInput = function() {
        return this.contentInput.getAttribute('value');
    };

    articalSelectLastOption = function() {
        this.articalSelect.all(by.tagName('option')).last().click();
    };

    articalSelectOption = function(option) {
        this.articalSelect.sendKeys(option);
    };

    getArticalSelect = function() {
        return this.articalSelect;
    };

    getArticalSelectedOption = function() {
        return this.articalSelect.element(by.css('option:checked')).getText();
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
