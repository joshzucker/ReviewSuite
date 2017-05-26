import { User } from '../../shared';
import { CustomerMySuffix } from '../customer';
export class Review2MySuffix {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dateWritten?: any,
        public token?: string,
        public custExperiece?: any,
        public user?: User,
        public customer?: CustomerMySuffix,
    ) {
    }
}
