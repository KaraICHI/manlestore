import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ArticalCommentReply e2e test', () => {

    let navBarPage: NavBarPage;
    let articalCommentReplyDialogPage: ArticalCommentReplyDialogPage;
    let articalCommentReplyComponentsPage: ArticalCommentReplyComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ArticalCommentReplies', () => {
        navBarPage.goToEntity('artical-comment-reply');
        articalCommentReplyComponentsPage = new ArticalCommentReplyComponentsPage();
        expect(articalCommentReplyComponentsPage.getTitle())
            .toMatch(/manleApp.articalCommentReply.home.title/);

    });

    it('should load create ArticalCommentReply dialog', () => {
        articalCommentReplyComponentsPage.clickOnCreateButton();
        articalCommentReplyDialogPage = new ArticalCommentReplyDialogPage();
        expect(articalCommentReplyDialogPage.getModalTitle())
            .toMatch(/manleApp.articalCommentReply.home.createOrEditLabel/);
        articalCommentReplyDialogPage.close();
    });

    it('should create and save ArticalCommentReplies', () => {
        articalCommentReplyComponentsPage.clickOnCreateButton();
        articalCommentReplyDialogPage.setContentInput('content');
        expect(articalCommentReplyDialogPage.getContentInput()).toMatch('content');
        articalCommentReplyDialogPage.setCreatDateInput('2000-12-31');
        expect(articalCommentReplyDialogPage.getCreatDateInput()).toMatch('2000-12-31');
        articalCommentReplyDialogPage.articalCommentSelectLastOption();
        articalCommentReplyDialogPage.clientUserSelectLastOption();
        articalCommentReplyDialogPage.save();
        expect(articalCommentReplyDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ArticalCommentReplyComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-artical-comment-reply div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ArticalCommentReplyDialogPage {
    modalTitle = element(by.css('h4#myArticalCommentReplyLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    contentInput = element(by.css('input#field_content'));
    creatDateInput = element(by.css('input#field_creatDate'));
    articalCommentSelect = element(by.css('select#field_articalComment'));
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

    setCreatDateInput = function(creatDate) {
        this.creatDateInput.sendKeys(creatDate);
    };

    getCreatDateInput = function() {
        return this.creatDateInput.getAttribute('value');
    };

    articalCommentSelectLastOption = function() {
        this.articalCommentSelect.all(by.tagName('option')).last().click();
    };

    articalCommentSelectOption = function(option) {
        this.articalCommentSelect.sendKeys(option);
    };

    getArticalCommentSelect = function() {
        return this.articalCommentSelect;
    };

    getArticalCommentSelectedOption = function() {
        return this.articalCommentSelect.element(by.css('option:checked')).getText();
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
