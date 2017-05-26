import { User } from '../../shared';
import { CustomerMySuffix } from '../customer';
export class CustomerAccessTokenMySuffix {
    constructor(
        public id?: number,
        public expiryDate?: any,
        public token?: string,
        public user?: User,
        public customer?: CustomerMySuffix,
    ) {
    }
}
