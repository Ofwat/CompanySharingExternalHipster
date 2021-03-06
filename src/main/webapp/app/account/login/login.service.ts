import { Injectable } from '@angular/core';
import { Principal } from '../../shared/auth/principal.service';
import { AuthServerProvider } from '../../shared/auth/auth-session.service';

@Injectable()
export class LoginService {

    loginFailureCode: string;

    constructor(
        private principal: Principal,
        private authServerProvider: AuthServerProvider
    ) {}

    login(credentials, callback?) {
        const cb = callback || function() {};

        return new Promise((resolve, reject) => {
            this.authServerProvider.login(credentials).subscribe((data) => {
                this.principal.identity(true).then((account) => {
                    resolve(data);
                });
                return cb();
            }, (err) => {
                this.logout();
                reject(err);
                return cb(err);
            });
        });
    }

    logout() {
        this.authServerProvider.logout().subscribe();
        this.principal.authenticate(null);
    }
}
