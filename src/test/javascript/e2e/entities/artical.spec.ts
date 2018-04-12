import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Artical e2e test', () => {

    let navBarPage: NavBarPage;
    let articalDialogPage: ArticalDialogPage;
    let articalComponentsPage: ArticalComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Articals', () => {
        navBarPage.goToEntity('artical');
        articalComponentsPage = new ArticalComponentsPage();
        expect(articalComponentsPage.getTitle())
            .toMatch(/manleApp.artical.home.title/);

    });

    it('should load create Artical dialog', () => {
        articalComponentsPage.clickOnCreateButton();
        articalDialogPage = new ArticalDialogPage();
        expect(articalDialogPage.getModalTitle())
            .toMatch(/manleApp.artical.home.createOrEditLabel/);
        articalDialogPage.close();
    });

    it('should create and save Articals', () => {
        articalComponentsPage.clickOnCreateButton();
        articalDialogPage.setTitleInput('title');
        expect(articalDialogPage.getTitleInput()).toMatch('title');
        articalDialogPage.setContentInput('content');
        expect(articalDialogPage.getContentInput()).toMatch('content');
        articalDialogPage.setFigureInput('figure');
        expect(articalDialogPage.getFigureInput()).toMatch('figure');
        articalDialogPage.setCreatDateInput('2000-12-31');
        expect(articalDialogPage.getCreatDateInput()).toMatch('2000-12-31');
        articalDialogPage.clientUserSelectLastOption();
        articalDialogPage.save();
        expect(articalDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ArticalComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-artical div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ArticalDialogPage {
    modalTitle = element(by.css('h4#myArticalLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    contentInput = element(by.css('input#field_content'));
    figureInput = element(by.css('input#field_figure'));
    creatDateInput = element(by.css('input#field_creatDate'));
    clientUserSelect = element(by.css('select#field_clientUser'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setContentInput = function(content) {
        this.contentInput.sendKeys(content);
    };

    getContentInput = function() {
        return this.contentInput.getAttribute('value');
    };

    setFigureInput = function(figure) {
        this.figureInput.sendKeys(figure);
    };

    getFigureInput = function() {
        return this.figureInput.getAttribute('value');
    };

    setCreatDateInput = function(creatDate) {
        this.creatDateInput.sendKeys(creatDate);
    };

    getCreatDateInput = function() {
        return this.creatDateInput.getAttribute('value');
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
