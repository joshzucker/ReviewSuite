import { User } from '../../shared';
import { CustomerMySuffix } from '../customer';
export class Review1MySuffix {
    constructor(
        public id?: number,
        public rating?: number,
        public dateWritten?: any,
        public token?: string,
        public selectedLink?: string,
        public user?: User,
        public customer?: CustomerMySuffix,
    ) {
    }
}
