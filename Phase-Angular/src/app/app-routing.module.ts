import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeplanComponent } from './back/planback/listeplan/listeplan.component';
import { AddplanComponent } from './back/planback/addplan/addplan.component';
import { ListecommentComponent } from './back/forumback/listecomment/listecomment.component';
import { UpdateplanComponent } from './back/planback/updateplan/updateplan.component';

import { AuthGuard } from './utils/auth-guard';
import { LoginComponent } from './front/userfront/login/login.component';
import { ListUsersComponent } from './back/userback/list-users/list-users.component';
import { AdduserComponent } from './back/userback/adduser/adduser.component';
import { ProfileComponent } from './front/userfront/profile/profile.component';
import { RegisterComponent } from './front/userfront/register/register.component';
import { UpdateuserComponent } from './back/userback/updateuser/updateuser.component';



import { ContractPlanComponent } from './back/contract-plan/contract-plan.component';
import { ListeContractPlanComponent } from './back/contract-plan/liste-contract-plan/liste-contract-plan.component';
import { AddContractPlanComponent } from './back/contract-plan/add-contract-plan/add-contract-plan.component';

import { ListePlanFrontComponent } from './front/liste-plan-front/liste-plan-front.component';
import { ListeRentalOfferComponent } from './back/rentalOffer/liste-rental-offer/liste-rental-offer.component';
import { AjoutRentalOfferComponent } from './back/rentalOffer/ajout-rental-offer/ajout-rental-offer.component';
import { UpdateRentalOfferComponent } from './back/rentalOffer/update-rental-offer/update-rental-offer.component';
import { RentalContratComponent } from './back/rental-contrat/rental-contrat.component';
import { AddRentalContratComponent } from './back/rental-contrat/add-rental-contrat/add-rental-contrat.component';

import { ListeContratComponent } from './back/rental-contrat/liste-contrat/liste-contrat.component';
import { ListeRentalFrontComponent } from './front/rentalOfferFront/liste-rental-front/liste-rental-front.component';
import { ContratParOffreFrontComponent } from './front/rentalOfferFront/contrat-par-offre-front/contrat-par-offre-front.component';
import { AddRentalContratFrontComponent } from './front/rentalOfferFront/add-rental-contrat-front/add-rental-contrat-front.component';

import { AddcommentComponent } from './back/forumback/addcomment/addcomment.component';
import { PostListComponent } from './front/forumfront/post-list/post-list.component';
import { AddPostComponent } from './back/forumback/add-post/add-post.component';
import { ListPostComponent } from './back/forumback/list-post/list-post.component';
import { UpdatePostComponent } from './back/forumback/update-post/update-post.component';
import { ForgotPasswordComponent } from './front/userfront/forgot-password/forgot-password.component';
import { ConfirmregisterComponent } from './front/userfront/confirmregister/confirmregister.component';
import { ChangepasswordComponent } from './front/userfront/changepassword/changepassword.component';
import { TypeemailComponent } from './front/userfront/typeemail/typeemail.component';
import { CookieService } from 'ngx-cookie-service';
import { ConfirmPasswordComponent } from './front/userfront/confirm-password/confirm-password.component';
import { PlanContractFrontComponent } from './front/plan-contract-front/plan-contract-front.component';
import { ChoicePlanComponent } from './front/choice-plan/choice-plan.component';

import { AdddeliverybackComponent } from './back/deliveryback/adddeliveryback/adddeliveryback.component';
import { ListdeliverybackComponent } from './back/deliveryback/listdeliveryback/listdeliveryback.component';
import { ListfurniturebackComponent } from './back/furnitureback/listfurnitureback/listfurnitureback.component';
import { ListrelocationbackComponent } from './back/relocationback/listrelocationback/listrelocationback.component';
import { AddfurniturebackComponent } from './back/furnitureback/addfurnitureback/addfurnitureback.component';
import { AddrelocationbackComponent } from './back/relocationback/addrelocationback/addrelocationback.component';
import { UpdatedeliverybackComponent } from './back/deliveryback/updatedeliveryback/updatedeliveryback.component';
import { UpdaterelocationbackComponent } from './back/relocationback/updaterelocationback/updaterelocationback.component';
import { UpdatefurniturebackComponent } from './back/furnitureback/updatefurnitureback/updatefurnitureback.component';
import { PayementComponent } from './back/payement/payement/payement.component';
import { AddpaymentfrontComponent } from './front/addpaymentfront/addpaymentfront.component';
import { CalendarComponentComponent } from './front/deliveryfront/calendar-component/calendar-component.component';
import { AddfurniturefrontComponent } from './front/furniturefront/addfurniturefront/addfurniturefront.component';
import { ListrelocationfrontComponent } from './front/Relocationfront/listrelocationfront/listrelocationfront.component';
import { AddrelocationfrontComponent } from './front/Relocationfront/addrelocationfront/addrelocationfront.component';
import { FurniturefrontComponent } from './front/furniturefront/furniturefront/furniturefront.component';
import { ListfurniturerelocateurComponent } from './front/furniturefront/listfurniturerelocateur/listfurniturerelocateur.component';
import { ContentfrontComponent } from './front/contentfront/contentfront.component';
import { ListdeliveryfrontComponent } from './front/deliveryfront/listdeliveryfront/listdeliveryfront.component';
import { AdddeliveryfrontComponent } from './front/deliveryfront/adddeliveryfront/adddeliveryfront.component';



import { ListeofferComponent } from './back/offerback/listeoffer/listeoffer.component';
import { AddcontractComponent } from './back/contractback/addcontract/addcontract.component';
import { ListecontractComponent } from './back/contractback/listecontract/listecontract.component';
import { NavbarfrontComponent } from './front/navbarfront/navbarfront.component';
import { AddofferfrontComponent } from './front/offerfront/addofferfront/addofferfront.component';
import { ListeofferfrontComponent } from './front/offerfront/listeofferfront/listeofferfront.component';
import { AddcontractfrontComponent } from './front/contractfront/addcontractfront/addcontractfront.component';
import { ListecontractfrontComponent } from './front/contractfront/listecontractfront/listecontractfront.component';
import { UpdateoffrebackComponent } from './back/offerback/updateofferback/updateoffreback/updateoffreback.component';
import { AddrendezvousComponent } from './front/offerfront/addrendezvous/addrendezvous.component';
import { ListeofferfavoriteComponent } from './back/offerback/listeofferfavorite/listeofferfavorite.component';
import { ListefavoritefrontComponent } from './front/offerfront/listefavoritefront/listefavoritefront.component';
import { OfferpaymentComponent } from './front/contractfront/offerpayment/offerpayment.component';
import { AddofferComponent } from './back/offerback/addoffer/addoffer.component';
import { BusinessnewsComponent } from './front/forumfront/businessnews/businessnews.component';
import { PostDetailsComponent } from './front/forumfront/post-details/post-details.component';
import { AddtaxpaymentComponent } from './front/addpaymentfront/addtaxpayment/addtaxpayment.component';
import { Business1newsComponent } from './front/forumfront/business1news/business1news.component';
import { Business2newsComponent } from './front/forumfront/business2news/business2news.component';



import { DetailsContratComponent } from './front/rentalOfferFront/details-contrat/details-contrat.component';
const routes: Routes = [
{path :'listOffers',component: ListeplanComponent},
{path :'addplan',component: AddplanComponent},
{path :'listcomment',component: ListecommentComponent},
{path :'updateplan/:id',component: UpdateplanComponent},
{ path: 'login', component: LoginComponent, },
{path: 'register', component: RegisterComponent },
{path: 'users', component: ListUsersComponent,canActivate:[AuthGuard],data:{permittedRole:"ADMIN"}},
{path: 'addusers', component: AdduserComponent},
{path: 'profile', component: ProfileComponent,canActivate:[AuthGuard],data:{permittedRole:"CLIENT"}},
{path: 'updateuser/:userid', component: UpdateuserComponent,canActivate:[AuthGuard]},
{path :'addcomment',component: AddcommentComponent},
{path: 'listeContratPlan/:id', component: ContractPlanComponent },
{path :'listcontractplan',component: ListeContractPlanComponent},
{path: 'listfront', component: ListePlanFrontComponent },
{path: 'listeContratplan/:id/ajout', component: AddContractPlanComponent },
{path: 'listRentalOffers', component: ListeRentalOfferComponent },
{path: 'AddRentalOffer', component: AjoutRentalOfferComponent },
{path: 'UpdateRentalOffer/:idOffre', component: UpdateRentalOfferComponent },
{path: 'listeContrat/:id', component: RentalContratComponent },
{path: 'listeContrat/:id/ajout', component: AddRentalContratComponent },
{path: 'listRentalContrats', component: ListeContratComponent },
{path: 'listRentalOffersFront', component: ListeRentalFrontComponent },
{path: 'listeContratFront/:id', component: ContratParOffreFrontComponent },
{path: 'listeContratFront/:id/ajout', component: AddRentalContratFrontComponent },



{path: 'posts/:id', component: PostDetailsComponent},


{path :'news',component: BusinessnewsComponent},


{ path: 'listfront/:idOffre', component: PlanContractFrontComponent },
{ path: 'choiceplan', component: ChoicePlanComponent },


{ path: 'DetailsContrat/:id', component: DetailsContratComponent },


{path: 'category/:id', component: PostListComponent},
{path: 'category', component: PostListComponent},
{path: 'posts', component: PostListComponent},
{path :'addPost',component: AddPostComponent},
{path :'listpost',component: ListPostComponent},
{path :'forgotpwd',component: ForgotPasswordComponent},
{path :'updatepost/:id',component: UpdatePostComponent},
 { path: 'confirmRegister', component: ConfirmregisterComponent },
 {path :'changepassword',component: ChangepasswordComponent},
{path :'typeEmail',component: TypeemailComponent},
{path :'conf',component: ConfirmPasswordComponent},
{path :'updatepost/:id',component: UpdatePostComponent},

{path: 'adddeliveryback/:id', component: AdddeliverybackComponent},
{path: 'listdeliveryback', component: ListdeliverybackComponent},
{path: 'listfurnitureback', component: ListfurniturebackComponent},
{path: 'addfurnitureback/:id', component: AddfurniturebackComponent},
{path: 'addfurniturefront/:id', component: AddfurniturefrontComponent},
{path: 'listrelocationback', component: ListrelocationbackComponent},
{path: 'addrelocationback', component: AddrelocationbackComponent},
{path: 'updatedeliveryback/:deliveryid', component: UpdatedeliverybackComponent},
{path: 'updaterelocationback/:deliveryid', component: UpdaterelocationbackComponent},
{path: 'updatefurnitureback/:deliveryid', component: UpdatefurniturebackComponent},
{path: 'listpayement', component: PayementComponent},
{path: 'addpayment/:id', component: AddpaymentfrontComponent},
{path: 'DeliveryFront', component: CalendarComponentComponent},
{path: 'addfurniturefront/:id', component:AddfurniturefrontComponent},
{path: 'listrelocationfront', component: ListrelocationfrontComponent},
{path: 'addrelocationfront', component: AddrelocationfrontComponent},
{path: 'listfurniturefrontclient', component:FurniturefrontComponent},
{path: 'listfurniturefront', component:ListfurniturerelocateurComponent},
{path: 'home', component:ContentfrontComponent},
{path: 'listdeliveryfrontclient', component:ListdeliveryfrontComponent},
{path: 'adddeliveryfront/:id', component: AdddeliveryfrontComponent},
{path: 'addpaymenttax', component: AddtaxpaymentComponent},


{path :'news1',component: Business1newsComponent},
{path :'news2',component: Business2newsComponent},
{path : 'addoffer',component: AddofferComponent},
{path : 'listeoffer',component: ListeofferComponent},
{path : 'addcontract/:id',component: AddcontractComponent},
{path : 'listecontract',component: ListecontractComponent},
{path : 'navbarfront',component:NavbarfrontComponent},
{path : 'addofferfront',component:AddofferfrontComponent},
{path : 'listeofferfront',component:ListeofferfrontComponent},
{path : 'addcontractfront/:id',component:AddcontractfrontComponent},
{path : 'listecontractfront',component:ListecontractfrontComponent},
{path : 'updateoffreback/:id',component:UpdateoffrebackComponent},
{path : 'addrendezvous/:id',component:AddrendezvousComponent},
{path : 'listeofferfavorite',component:ListeofferfavoriteComponent},
{path : 'listefavoritefront',component:ListefavoritefrontComponent},
{path : 'paymentcontract/:id',component:OfferpaymentComponent},


{path :'updatepost/:id',component: UpdatePostComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

