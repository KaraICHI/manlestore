import { BaseEntity } from './../../shared';

export class Theme implements BaseEntity {
    constructor(
        public id?: number,
        public themeName?: string,
    ) {
    }
}
