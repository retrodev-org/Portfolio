namespace COP2360_week4forms
{
    partial class Exceptions
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Exceptions));
            this.txtSecondNumber = new System.Windows.Forms.TextBox();
            this.btnAdd = new System.Windows.Forms.Button();
            this.btnSubtract = new System.Windows.Forms.Button();
            this.btnDivide = new System.Windows.Forms.Button();
            this.btnMultiply = new System.Windows.Forms.Button();
            this.lblEqual = new System.Windows.Forms.Label();
            this.txtFirstNumber = new System.Windows.Forms.TextBox();
            this.lblResult = new System.Windows.Forms.Label();
            this.btnModulus = new System.Windows.Forms.Button();
            this.btnExit = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // txtSecondNumber
            // 
            this.txtSecondNumber.CausesValidation = false;
            this.txtSecondNumber.Location = new System.Drawing.Point(204, 48);
            this.txtSecondNumber.Name = "txtSecondNumber";
            this.txtSecondNumber.Size = new System.Drawing.Size(89, 20);
            this.txtSecondNumber.TabIndex = 1;
            this.txtSecondNumber.Text = "0";
            this.txtSecondNumber.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // btnAdd
            // 
            this.btnAdd.CausesValidation = false;
            this.btnAdd.Location = new System.Drawing.Point(135, 47);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(32, 26);
            this.btnAdd.TabIndex = 1;
            this.btnAdd.TabStop = false;
            this.btnAdd.Text = "+";
            this.btnAdd.UseVisualStyleBackColor = true;
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // btnSubtract
            // 
            this.btnSubtract.CausesValidation = false;
            this.btnSubtract.Location = new System.Drawing.Point(135, 76);
            this.btnSubtract.Name = "btnSubtract";
            this.btnSubtract.Size = new System.Drawing.Size(32, 26);
            this.btnSubtract.TabIndex = 2;
            this.btnSubtract.TabStop = false;
            this.btnSubtract.Text = "-";
            this.btnSubtract.UseVisualStyleBackColor = true;
            this.btnSubtract.Click += new System.EventHandler(this.btnSubtract_Click);
            // 
            // btnDivide
            // 
            this.btnDivide.CausesValidation = false;
            this.btnDivide.Location = new System.Drawing.Point(135, 134);
            this.btnDivide.Name = "btnDivide";
            this.btnDivide.Size = new System.Drawing.Size(32, 26);
            this.btnDivide.TabIndex = 3;
            this.btnDivide.TabStop = false;
            this.btnDivide.Text = "/";
            this.btnDivide.UseVisualStyleBackColor = true;
            this.btnDivide.Click += new System.EventHandler(this.btnDivide_Click);
            // 
            // btnMultiply
            // 
            this.btnMultiply.CausesValidation = false;
            this.btnMultiply.Location = new System.Drawing.Point(135, 105);
            this.btnMultiply.Name = "btnMultiply";
            this.btnMultiply.Size = new System.Drawing.Size(32, 26);
            this.btnMultiply.TabIndex = 4;
            this.btnMultiply.TabStop = false;
            this.btnMultiply.Text = "*";
            this.btnMultiply.UseVisualStyleBackColor = true;
            this.btnMultiply.Click += new System.EventHandler(this.btnMultiply_Click);
            // 
            // lblEqual
            // 
            this.lblEqual.AutoSize = true;
            this.lblEqual.CausesValidation = false;
            this.lblEqual.Location = new System.Drawing.Point(310, 54);
            this.lblEqual.Name = "lblEqual";
            this.lblEqual.Size = new System.Drawing.Size(13, 13);
            this.lblEqual.TabIndex = 5;
            this.lblEqual.Text = "=";
            // 
            // txtFirstNumber
            // 
            this.txtFirstNumber.CausesValidation = false;
            this.txtFirstNumber.Location = new System.Drawing.Point(14, 48);
            this.txtFirstNumber.Name = "txtFirstNumber";
            this.txtFirstNumber.Size = new System.Drawing.Size(89, 20);
            this.txtFirstNumber.TabIndex = 0;
            this.txtFirstNumber.Text = "0";
            this.txtFirstNumber.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // lblResult
            // 
            this.lblResult.AutoSize = true;
            this.lblResult.CausesValidation = false;
            this.lblResult.Location = new System.Drawing.Point(340, 54);
            this.lblResult.Name = "lblResult";
            this.lblResult.Size = new System.Drawing.Size(13, 13);
            this.lblResult.TabIndex = 7;
            this.lblResult.Text = "0";
            // 
            // btnModulus
            // 
            this.btnModulus.CausesValidation = false;
            this.btnModulus.Location = new System.Drawing.Point(135, 163);
            this.btnModulus.Name = "btnModulus";
            this.btnModulus.Size = new System.Drawing.Size(32, 26);
            this.btnModulus.TabIndex = 8;
            this.btnModulus.TabStop = false;
            this.btnModulus.Text = "%";
            this.btnModulus.UseVisualStyleBackColor = true;
            this.btnModulus.Click += new System.EventHandler(this.btnModulus_Click);
            // 
            // btnExit
            // 
            this.btnExit.CausesValidation = false;
            this.btnExit.Location = new System.Drawing.Point(303, 203);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(72, 26);
            this.btnExit.TabIndex = 9;
            this.btnExit.TabStop = false;
            this.btnExit.Text = "Goodbye";
            this.btnExit.UseVisualStyleBackColor = true;
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.ClientSize = new System.Drawing.Size(400, 262);
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.btnModulus);
            this.Controls.Add(this.lblResult);
            this.Controls.Add(this.btnMultiply);
            this.Controls.Add(this.btnDivide);
            this.Controls.Add(this.btnSubtract);
            this.Controls.Add(this.btnAdd);
            this.Controls.Add(this.txtFirstNumber);
            this.Controls.Add(this.lblEqual);
            this.Controls.Add(this.txtSecondNumber);
            this.Name = "Form1";
            this.Text = "Basic Calculator";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox txtSecondNumber;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.Button btnSubtract;
        private System.Windows.Forms.Button btnDivide;
        private System.Windows.Forms.Button btnMultiply;
        private System.Windows.Forms.Label lblEqual;
        private System.Windows.Forms.TextBox txtFirstNumber;
        private System.Windows.Forms.Label lblResult;
        private System.Windows.Forms.Button btnModulus;
        private System.Windows.Forms.Button btnExit;
    }
}

