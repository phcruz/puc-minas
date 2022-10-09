import { TabsModule } from './modulos/home/tabs.module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './util/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogMensagemComponent } from './dialogs/dialog-mensagem/dialog-mensagem.component';
import { MAT_DATE_LOCALE } from '@angular/material';
import { DialogConfirmacaoComponent } from './dialogs/dialog-confirmacao/dialog-confirmacao.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoaderInterceptorService } from './loader/loader-interceptor/loader-interceptor.service';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { LoaderConfig } from './loader/loader-enum';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { AppHomeComponent } from './modulos/home/app-home/app-home.component';
import { AppToolComponent } from './modulos/tool/app-tool/app-tool.component';
import { ToolModule } from './modulos/tool/tool.module';
import { DrawerService } from './util/services/drawer.service';
import { DialogMensagemErroComponent } from './dialogs/dialog-mensagem-erro/dialog-mensagem-erro.component';
import { SocialLoginModule, AuthServiceConfig, LoginOpt } from 'angularx-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angularx-social-login';
import { environment } from 'src/environments/environment';
import { AuthService } from './util/guard/auth.service';
import { AuthGuard } from './util/guard/auth-guard';
import { DeviceDetectorModule } from 'ngx-device-detector';


const fbLoginOptions: LoginOpt = {
  scope: 'public_profile,email',
  return_scopes: true,
  enable_profile_selector: true
}; // https://developers.facebook.com/docs/reference/javascript/FB.login/v2.11

const googleLoginOptions: LoginOpt = {
  scope: 'profile email'
}; // https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiauth2clientconfig

const config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider(`${environment.CLIENT_ID_GOOGLE}`, googleLoginOptions)
  },
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider(`${environment.CLIENT_ID_FACEBOOK}`, fbLoginOptions)
  }
]);

export function provideConfig() {
  return config;
}

@NgModule({
   declarations: [
      AppComponent,
      DialogMensagemComponent,
      DialogConfirmacaoComponent,
      DialogMensagemErroComponent,
      ToolbarComponent,
      SideNavComponent,
      AppHomeComponent,
      AppToolComponent,
   ],
   imports: [
      BrowserModule,
      BrowserAnimationsModule,
      AppRoutingModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
      TabsModule,
      ToolModule,
      HttpClientModule,
      NgxUiLoaderModule.forRoot(LoaderConfig),
      SocialLoginModule,
      DeviceDetectorModule
   ],
   providers: [
    DrawerService,
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'pt-BR'
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptorService,
      multi: true
    },
    {
      provide: AuthServiceConfig,
      useFactory: provideConfig
    },
    AuthService,
    AuthGuard
   ],
   bootstrap: [
      AppComponent
   ],
   schemas: [CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: [
    DialogMensagemComponent,
    DialogConfirmacaoComponent,
    DialogMensagemErroComponent
  ]
})
export class AppModule { }
