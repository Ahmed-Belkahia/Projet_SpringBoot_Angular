import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './back/navbar/navbar.component';
import { SidebarComponent } from './back/sidebar/sidebar.component';
import { ContentComponent } from './back/content/content.component';
import { NavbarfrontComponent } from './front/navbarfront/navbarfront.component';
import { ContentfrontComponent } from './front/contentfront/contentfront.component';
import { FooterComponent } from './front/footer/footer.component';
import { ListeplanComponent } from './back/planback/listeplan/listeplan.component';
import { HttpClientModule } from '@angular/common/http';
import { ListecommentComponent } from './back/forumback/listecomment/listecomment.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { AddplanComponent } from './back/planback/addplan/addplan.component';
import { CommonModule } from '@angular/common';


import { AdduserComponent } from './back/userback/adduser/adduser.component';
import { ListUsersComponent } from './back/userback/list-users/list-users.component';
import { LoginComponent } from './front/userfront/login/login.component';
import { RegisterComponent } from './front/userfront/register/register.component';
import { ProfileComponent } from './front/userfront/profile/profile.component';
import { UpdateuserComponent } from './back/userback/updateuser/updateuser.component';

import { FormsModule,ReactiveFormsModule }   from '@angular/forms';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { UpdateplanComponent } from './back/planback/updateplan/updateplan.component';
import { ContractPlanComponent } from './back/contract-plan/contract-plan.component';
import { ListeContractPlanComponent } from './back/contract-plan/liste-contract-plan/liste-contract-plan.component';
import { AddContractPlanComponent } from './back/contract-plan/add-contract-plan/add-contract-plan.component';
import { WeatherWidegtComponent } from './back/planback/weather-widegt/weather-widegt.component';

import { PayementComponent } from './back/payement/payement/payement.component';
import { ListePlanFrontComponent } from './front/liste-plan-front/liste-plan-front.component';

import { ListeRentalOfferComponent } from './back/rentalOffer/liste-rental-offer/liste-rental-offer.component';
import { AjoutRentalOfferComponent } from './back/rentalOffer/ajout-rental-offer/ajout-rental-offer.component';
import { MatDialogModule } from '@angular/material/dialog';
import { UpdateRentalOfferComponent } from './back/rentalOffer/update-rental-offer/update-rental-offer.component';
import { RentalContratComponent } from './back/rental-contrat/rental-contrat.component';
import { AddRentalContratComponent } from './back/rental-contrat/add-rental-contrat/add-rental-contrat.component';
// import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ListeContratComponent } from './back/rental-contrat/liste-contrat/liste-contrat.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ListeRentalFrontComponent } from './front/rentalOfferFront/liste-rental-front/liste-rental-front.component';
import { RevenuByOfferComponent } from './back/rentalOffer/revenu-by-offer/revenu-by-offer.component';
import { ContratParOffreFrontComponent } from './front/rentalOfferFront/contrat-par-offre-front/contrat-par-offre-front.component';
import { AddRentalContratFrontComponent } from './front/rentalOfferFront/add-rental-contrat-front/add-rental-contrat-front.component';
import { AddcommentComponent } from './back/forumback/addcomment/addcomment.component';
import { PostListComponent } from './front/forumfront/post-list/post-list.component';
import { ListPostComponent } from './back/forumback/list-post/list-post.component';
import { AddPostComponent } from './back/forumback/add-post/add-post.component';
import { UpdatePostComponent } from './back/forumback/update-post/update-post.component';

import { ForgotPasswordComponent } from './front/userfront/forgot-password/forgot-password.component';
import { ConfirmregisterComponent } from './front/userfront/confirmregister/confirmregister.component';
import { ChangepasswordComponent } from './front/userfront/changepassword/changepassword.component';
import { TypeemailComponent } from './front/userfront/typeemail/typeemail.component';
import { ConfirmPasswordComponent } from './front/userfront/confirm-password/confirm-password.component';



import { PlanContractFrontComponent } from './front/plan-contract-front/plan-contract-front.component';
import { ChoicePlanComponent } from './front/choice-plan/choice-plan.component';
import { PayementPlanComponent } from './front/payement-plan/payement-plan.component';

import { ListdeliverybackComponent } from './back/deliveryback/listdeliveryback/listdeliveryback.component';
import { AdddeliverybackComponent } from './back/deliveryback/adddeliveryback/adddeliveryback.component';
import { UpdatedeliverybackComponent } from './back/deliveryback/updatedeliveryback/updatedeliveryback.component';
import { ListfurniturebackComponent } from './back/furnitureback/listfurnitureback/listfurnitureback.component';
import { AddfurniturebackComponent } from './back/furnitureback/addfurnitureback/addfurnitureback.component';
import { UpdatefurniturebackComponent } from './back/furnitureback/updatefurnitureback/updatefurnitureback.component';
import { ListrelocationbackComponent } from './back/relocationback/listrelocationback/listrelocationback.component';
import { AddrelocationbackComponent } from './back/relocationback/addrelocationback/addrelocationback.component';
import { UpdaterelocationbackComponent } from './back/relocationback/updaterelocationback/updaterelocationback.component';
import { ListdeliveryfrontComponent } from './front/deliveryfront/listdeliveryfront/listdeliveryfront.component';
import { AdddeliveryfrontComponent } from './front/deliveryfront/adddeliveryfront/adddeliveryfront.component';
import { CalendarComponentComponent } from './front/deliveryfront/calendar-component/calendar-component.component';
  import { CalendarModule, DateAdapter  } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AddpaymentfrontComponent } from './front/addpaymentfront/addpaymentfront.component';
import { FurniturefrontComponent } from './front/furniturefront/furniturefront/furniturefront.component';
import { AddfurniturefrontComponent } from './front/furniturefront/addfurniturefront/addfurniturefront.component';
import { ListrelocationfrontComponent } from './front/Relocationfront/listrelocationfront/listrelocationfront.component';
import { AddrelocationfrontComponent } from './front/Relocationfront/addrelocationfront/addrelocationfront.component';
import { ListfurniturerelocateurComponent } from './front/furniturefront/listfurniturerelocateur/listfurniturerelocateur.component';

import { AddofferComponent } from './back/offerback/addoffer/addoffer.component';
import { ListeofferComponent } from './back/offerback/listeoffer/listeoffer.component';
import { AddcontractComponent } from './back/contractback/addcontract/addcontract.component';
import { ListecontractComponent } from './back/contractback/listecontract/listecontract.component';
import { AddofferfrontComponent } from './front/offerfront/addofferfront/addofferfront.component';
import { ListeofferfrontComponent } from './front/offerfront/listeofferfront/listeofferfront.component';
import { AddcontractfrontComponent } from './front/contractfront/addcontractfront/addcontractfront.component';
import { ListecontractfrontComponent } from './front/contractfront/listecontractfront/listecontractfront.component';
import { UpdateoffrebackComponent } from './back/offerback/updateofferback/updateoffreback/updateoffreback.component';
import { AddrendezvousComponent } from './front/offerfront/addrendezvous/addrendezvous.component';
import { ListeofferfavoriteComponent } from './back/offerback/listeofferfavorite/listeofferfavorite.component';
import { ListefavoritefrontComponent } from './front/offerfront/listefavoritefront/listefavoritefront.component';
import { OfferpaymentComponent } from './front/contractfront/offerpayment/offerpayment.component';
import { BusinessnewsComponent } from './front/forumfront/businessnews/businessnews.component';
import { ChatbotComponent } from './front/forumfront/chatbot/chatbot.component';
// import { ServicePostService } from './shared/service-post.service';
import { PostDetailsComponent } from './front/forumfront/post-details/post-details.component';
import { CommentListComponent } from './front/forumfront/comment-list/comment-list.component';
import { CommentaddComponent } from './front/forumfront/commentadd/commentadd.component';
import { AddtaxpaymentComponent } from './front/addpaymentfront/addtaxpayment/addtaxpayment.component';
import { Business1newsComponent } from './front/forumfront/business1news/business1news.component';
import { Business2newsComponent } from './front/forumfront/business2news/business2news.component';



import { DetailsContratComponent } from './front/rentalOfferFront/details-contrat/details-contrat.component';
// import { ServicePostService } from './shared/service-post.service';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    ContentComponent,
    NavbarfrontComponent,
    ContentfrontComponent,
    FooterComponent,
    ListeplanComponent,
    ListecommentComponent,
    AddplanComponent,
    UpdateplanComponent,
    AddcommentComponent,
    ChatbotComponent,
    AdduserComponent,
    ListUsersComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    UpdateuserComponent,
    PostDetailsComponent,



    ContractPlanComponent,
    ListeContractPlanComponent,
    AddContractPlanComponent,
    WeatherWidegtComponent,


    PayementComponent,
    ListePlanFrontComponent,

    ListeRentalOfferComponent,
    AjoutRentalOfferComponent,
    UpdateRentalOfferComponent,
    RentalContratComponent,
    AddRentalContratComponent,
    ListeContratComponent,
    ListeRentalFrontComponent,
    RevenuByOfferComponent,
    ContratParOffreFrontComponent,
    AddRentalContratFrontComponent,

    PostListComponent,
    ListPostComponent,
    AddPostComponent,
    UpdatePostComponent,
    ForgotPasswordComponent,
    ConfirmregisterComponent,
    ChangepasswordComponent,
    TypeemailComponent,
    ConfirmPasswordComponent,
    PlanContractFrontComponent,
    ChoicePlanComponent,
    PayementPlanComponent,
    ListdeliverybackComponent,
    AdddeliverybackComponent,
    UpdatedeliverybackComponent,
    ListfurniturebackComponent,
    UpdatefurniturebackComponent,
    ListrelocationbackComponent,
    AddfurniturebackComponent,
    AddrelocationbackComponent,
    UpdaterelocationbackComponent,
    ListdeliveryfrontComponent,
    AdddeliveryfrontComponent,
    CalendarComponentComponent,
    AddpaymentfrontComponent,
    FurniturefrontComponent,
    AddfurniturefrontComponent,
    ListrelocationfrontComponent,
    AddrelocationfrontComponent,
    ListfurniturerelocateurComponent,




    AddofferComponent,
    ListeofferComponent,
    AddcontractComponent,
    ListecontractComponent,
    AddofferfrontComponent,
    ListeofferfrontComponent,
    AddcontractfrontComponent,
    ListecontractfrontComponent,
    UpdateoffrebackComponent,
    AddrendezvousComponent,
    ListeofferfavoriteComponent,
    ListefavoritefrontComponent,
    OfferpaymentComponent,
    BusinessnewsComponent,
    CommentListComponent,
    CommentaddComponent,
    AddtaxpaymentComponent,
    Business1newsComponent,
    Business2newsComponent,

    DetailsContratComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxPaginationModule,
    FormsModule,
    CommonModule,

    Ng2SearchPipeModule,

    ReactiveFormsModule,
    MatNativeDateModule,
    CommonModule,
    // ToastrModule.forRoot(),
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,

    MatDialogModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),

  ],
  providers: [],

  bootstrap: [AppComponent],

  exports: [CalendarComponentComponent],


})
export class AppModule { }
