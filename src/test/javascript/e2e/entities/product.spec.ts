import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Product e2e test', () => {

    let navBarPage: NavBarPage;
    let productDialogPage: ProductDialogPage;
    let productComponentsPage: ProductComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Products', () => {
        navBarPage.goToEntity('product');
        productComponentsPage = new ProductComponentsPage();
        expect(productComponentsPage.getTitle())
            .toMatch(/manleApp.product.home.title/);

    });

    it('should load create Product dialog', () => {
        productComponentsPage.clickOnCreateButton();
        productDialogPage = new ProductDialogPage();
        expect(productDialogPage.getModalTitle())
            .toMatch(/manleApp.product.home.createOrEditLabel/);
        productDialogPage.close();
    });

    it('should create and save Products', () => {
        productComponentsPage.clickOnCreateButton();
        productDialogPage.setProductNameInput('productName');
        expect(productDialogPage.getProductNameInput()).toMatch('productName');
        productDialogPage.setCoverPriceInput('5');
        expect(productDialogPage.getCoverPriceInput()).toMatch('5');
        productDialogPage.setOriginPriceInput('5');
        expect(productDialogPage.getOriginPriceInput()).toMatch('5');
        productDialogPage.setProduceDateInput('2000-12-31');
        expect(productDialogPage.getProduceDateInput()).toMatch('2000-12-31');
        productDialogPage.setPeriodInput('5');
        expect(productDialogPage.getPeriodInput()).toMatch('5');
        productDialogPage.setFigureInput('figure');
        expect(productDialogPage.getFigureInput()).toMatch('figure');
        productDialogPage.setBrandInput('brand');
        expect(productDialogPage.getBrandInput()).toMatch('brand');
        productDialogPage.setSupplyInput('supply');
        expect(productDialogPage.getSupplyInput()).toMatch('supply');
        productDialogPage.setBriefInput('brief');
        expect(productDialogPage.getBriefInput()).toMatch('brief');
        productDialogPage.setDescriptionInput('description');
        expect(productDialogPage.getDescriptionInput()).toMatch('description');
        productDialogPage.categorySelectLastOption();
        productDialogPage.themeSelectLastOption();
        productDialogPage.save();
        expect(productDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProductComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-product div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProductDialogPage {
    modalTitle = element(by.css('h4#myProductLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productNameInput = element(by.css('input#field_productName'));
    coverPriceInput = element(by.css('input#field_coverPrice'));
    originPriceInput = element(by.css('input#field_originPrice'));
    produceDateInput = element(by.css('input#field_produceDate'));
    periodInput = element(by.css('input#field_period'));
    figureInput = element(by.css('input#field_figure'));
    brandInput = element(by.css('input#field_brand'));
    supplyInput = element(by.css('input#field_supply'));
    briefInput = element(by.css('input#field_brief'));
    descriptionInput = element(by.css('input#field_description'));
    categorySelect = element(by.css('select#field_category'));
    themeSelect = element(by.css('select#field_theme'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setProductNameInput = function(productName) {
        this.productNameInput.sendKeys(productName);
    };

    getProductNameInput = function() {
        return this.productNameInput.getAttribute('value');
    };

    setCoverPriceInput = function(coverPrice) {
        this.coverPriceInput.sendKeys(coverPrice);
    };

    getCoverPriceInput = function() {
        return this.coverPriceInput.getAttribute('value');
    };

    setOriginPriceInput = function(originPrice) {
        this.originPriceInput.sendKeys(originPrice);
    };

    getOriginPriceInput = function() {
        return this.originPriceInput.getAttribute('value');
    };

    setProduceDateInput = function(produceDate) {
        this.produceDateInput.sendKeys(produceDate);
    };

    getProduceDateInput = function() {
        return this.produceDateInput.getAttribute('value');
    };

    setPeriodInput = function(period) {
        this.periodInput.sendKeys(period);
    };

    getPeriodInput = function() {
        return this.periodInput.getAttribute('value');
    };

    setFigureInput = function(figure) {
        this.figureInput.sendKeys(figure);
    };

    getFigureInput = function() {
        return this.figureInput.getAttribute('value');
    };

    setBrandInput = function(brand) {
        this.brandInput.sendKeys(brand);
    };

    getBrandInput = function() {
        return this.brandInput.getAttribute('value');
    };

    setSupplyInput = function(supply) {
        this.supplyInput.sendKeys(supply);
    };

    getSupplyInput = function() {
        return this.supplyInput.getAttribute('value');
    };

    setBriefInput = function(brief) {
        this.briefInput.sendKeys(brief);
    };

    getBriefInput = function() {
        return this.briefInput.getAttribute('value');
    };

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    };

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    };

    categorySelectLastOption = function() {
        this.categorySelect.all(by.tagName('option')).last().click();
    };

    categorySelectOption = function(option) {
        this.categorySelect.sendKeys(option);
    };

    getCategorySelect = function() {
        return this.categorySelect;
    };

    getCategorySelectedOption = function() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    };

    themeSelectLastOption = function() {
        this.themeSelect.all(by.tagName('option')).last().click();
    };

    themeSelectOption = function(option) {
        this.themeSelect.sendKeys(option);
    };

    getThemeSelect = function() {
        return this.themeSelect;
    };

    getThemeSelectedOption = function() {
        return this.themeSelect.element(by.css('option:checked')).getText();
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
