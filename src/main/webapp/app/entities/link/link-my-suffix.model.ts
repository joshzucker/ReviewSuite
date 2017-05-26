
export const enum SocialLinks {
    'GOOGLE',
    'FACEBOOK',
    'YELP',
    'RATEMD'

};
import { User } from '../../shared';
export class LinkMySuffix {
    constructor(
        public id?: number,
        public url?: string,
        public type?: SocialLinks,
        public user?: User,
    ) {
    }
}
