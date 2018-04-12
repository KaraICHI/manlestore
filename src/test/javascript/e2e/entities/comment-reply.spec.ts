import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CommentReply e2e test', () => {

    let navBarPage: NavBarPage;
    let commentReplyDialogPage: CommentReplyDialogPage;
    let commentReplyComponentsPage: CommentReplyComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CommentReplies', () => {
        navBarPage.goToEntity('comment-reply');
        commentReplyComponentsPage = new CommentReplyComponentsPage();
        expect(commentReplyComponentsPage.getTitle())
            .toMatch(/manleApp.commentReply.home.title/);

    });

    it('should load create CommentReply dialog', () => {
        commentReplyComponentsPage.clickOnCreateButton();
        commentReplyDialogPage = new CommentReplyDialogPage();
        expect(commentReplyDialogPage.getModalTitle())
            .toMatch(/manleApp.commentReply.home.createOrEditLabel/);
        commentReplyDialogPage.close();
    });

    it('should create and save CommentReplies', () => {
        commentReplyComponentsPage.clickOnCreateButton();
        commentReplyDialogPage.setContentInput('content');
        expect(commentReplyDialogPage.getContentInput()).toMatch('content');
        commentReplyDialogPage.setCreatDateInput('2000-12-31');
        expect(commentReplyDialogPage.getCreatDateInput()).toMatch('2000-12-31');
        commentReplyDialogPage.orderCommentSelectLastOption();
        commentReplyDialogPage.clientUserSelectLastOption();
        commentReplyDialogPage.save();
        expect(commentReplyDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommentReplyComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-comment-reply div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CommentReplyDialogPage {
    modalTitle = element(by.css('h4#myCommentReplyLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    contentInput = element(by.css('input#field_content'));
    creatDateInput = element(by.css('input#field_creatDate'));
    orderCommentSelect = element(by.css('select#field_orderComment'));
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

    orderCommentSelectLastOption = function() {
        this.orderCommentSelect.all(by.tagName('option')).last().click();
    };

    orderCommentSelectOption = function(option) {
        this.orderCommentSelect.sendKeys(option);
    };

    getOrderCommentSelect = function() {
        return this.orderCommentSelect;
    };

    getOrderCommentSelectedOption = function() {
        return this.orderCommentSelect.element(by.css('option:checked')).getText();
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
