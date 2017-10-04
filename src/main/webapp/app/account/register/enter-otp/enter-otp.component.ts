import {Component, OnInit, AfterViewInit, Renderer, ElementRef, Input} from '@angular/core';
import { Register } from '../register.service';

@Component({
    selector: 'jhi-enter-otp',
    templateUrl: './enter-otp.component.html'
})
export class EnterOtpComponent {

    @Input() email: string;
    resendOTP: Boolean;
    // success: boolean;
    code: string;
    otpError: boolean;
    verifySuccess: boolean;

    constructor(private registerService: Register) {
        this.resendOTP = false;
        this.code = '';
        this.otpError = false;
        this.verifySuccess = false;
    }

    notRecievedMessage() {
        this.resendOTP = true;
    }

    verifyCode() {
        this.registerService.verifyOtp(this.email, this.code).subscribe(() => {
            this.otpError = false;
            this.code = '';
            // Show the success - if they haven't verified their account from the email link then they need to click on the link.
            // if they have then take them to their account page... OR... take them to the account page and not show anything until the account is activated.
            // prefererably the first.
            this.verifySuccess = true;
        }, (response) => this.processVerifyOtpError(response));
    }

    resendOtp() {
        // Re-send the code and navigate to the enter OTP screen
        this.code = '';
        this.registerService.resendOtp(this.email).subscribe(() => {
            // this.success = true;
            this.resendOTP = false;
        }, (response) => this.processResendOtpError(response));
    }

    processResendOtpError( response: any ) {
        // TODO Add some error handling - see registration form.
        console.error('There was a problem sending the SMS');
    }

    processVerifyOtpError( response: any ) {
        // TODO Add some error handling - see registration form.
        // We need to deal with an invalid code.
        this.otpError = true;
    }

};
