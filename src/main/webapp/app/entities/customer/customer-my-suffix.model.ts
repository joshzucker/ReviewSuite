import { User } from '../../shared';
export class CustomerMySuffix {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public dateSent?: any,
        public dateReceived?: any,
        public reviewId?: number,
        public isReview1EmailClicked?: boolean,
        public isReview2EmailClicked?: boolean,
        public user?: User,
    ) {
        this.isReview1EmailClicked = false;
        this.isReview2EmailClicked = false;
    }
}
