import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatCardModule, MatDividerModule, MatProgressBarModule, MatButtonModule, MatRadioModule,
  MatFormFieldModule, MatInputModule, MatTabsModule, MatCheckboxModule, MatIconModule,
  MatSelectModule, MatNativeDateModule, MatDialogModule, MatBadgeModule, MatListModule,
  MatGridListModule, MatTooltipModule, MatMenuModule, MatSidenavModule, MatSnackBarModule,
  MatSlideToggleModule,
  MatProgressSpinnerModule
} from '@angular/material';
import { MatDatepickerModule } from '@angular/material/datepicker';

@NgModule({
  imports: [ MatCardModule, MatDividerModule, MatProgressBarModule, MatButtonModule, MatRadioModule,
    MatFormFieldModule, MatInputModule, MatTabsModule, MatCheckboxModule, MatIconModule,
    MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatDialogModule, MatListModule,
    MatGridListModule, MatTooltipModule, MatMenuModule, MatSidenavModule, MatSnackBarModule, MatProgressSpinnerModule, CommonModule],
  exports: [ MatCardModule, MatDividerModule, MatProgressBarModule, MatButtonModule, MatRadioModule,
    MatFormFieldModule, MatInputModule, MatTabsModule, MatCheckboxModule, MatSlideToggleModule, MatIconModule,
    MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatDialogModule, MatListModule,
    MatGridListModule, MatTooltipModule, MatMenuModule, MatSidenavModule, MatSnackBarModule, MatProgressSpinnerModule, CommonModule ]
})
export class MaterialModule {}
