import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Theme e2e test', () => {

    let navBarPage: NavBarPage;
    let themeDialogPage: ThemeDialogPage;
    let themeComponentsPage: ThemeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Themes', () => {
        navBarPage.goToEntity('theme');
        themeComponentsPage = new ThemeComponentsPage();
        expect(themeComponentsPage.getTitle())
            .toMatch(/manleApp.theme.home.title/);

    });

    it('should load create Theme dialog', () => {
        themeComponentsPage.clickOnCreateButton();
        themeDialogPage = new ThemeDialogPage();
        expect(themeDialogPage.getModalTitle())
            .toMatch(/manleApp.theme.home.createOrEditLabel/);
        themeDialogPage.close();
    });

    it('should create and save Themes', () => {
        themeComponentsPage.clickOnCreateButton();
        themeDialogPage.setThemeNameInput('themeName');
        expect(themeDialogPage.getThemeNameInput()).toMatch('themeName');
        themeDialogPage.save();
        expect(themeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ThemeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-theme div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ThemeDialogPage {
    modalTitle = element(by.css('h4#myThemeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    themeNameInput = element(by.css('input#field_themeName'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setThemeNameInput = function(themeName) {
        this.themeNameInput.sendKeys(themeName);
    };

    getThemeNameInput = function() {
        return this.themeNameInput.getAttribute('value');
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
