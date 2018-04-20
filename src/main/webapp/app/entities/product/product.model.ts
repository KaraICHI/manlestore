import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public productName?: string,
        public coverPrice?: number,
        public originPrice?: number,
        public produceDate?: any,
        public period?: number,
        public figure?: string,
        public brand?: string,
        public supply?: string,
        public brief?: string,
        public description?: string,
        public stock?: number,
        public sell?: number,
        public categoryCategoryName?: string,
        public categoryId?: number,
        public themeThemeName?: string,
        public themeId?: number,
        public clientUsers?: BaseEntity[],
    ) {
    }
}
