package com.as.travela;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Terms extends Activity
{
	Button b;
	TextView t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		String s="Terms and Conditions "
			+ "\n Terms of Use of the Application  "
			+ "\n This application is published by TRAVELA Wheels on rent, The person responsible for publication is Mr. Husain Ezzy,Chief Excutive Officer of TRAVELA_ Wheels on rent."
			
			+"\n \n Acceptance of the Terms & Conditions "
			+"\n Please read these Terms & Conditions carefully before using a TRAVELA Application or making reservations.They can be accessed via the hyperlink on the footer of each page on our Application. You are advised to print them out and keep a copy."
			+"\n Use of a TRAVELA Application implies acceptance without reserve of these Terms & Conditions, which include our Privacy Policy.These Terms & Conditions are in addition to the special conditions valid for each type of product or service offered on the app."
			
			+ "\n \n Use \n "
			+ "You undertake: \n"
			+"(a)Not to use a TRAVELA Application for illegal purposes or purposes that may damage the rights of TRAVELA International or third parties, and in particular, not to use the app to distribute damaging or illegal information, or information that is distasteful, discriminatory or offensive towards TRAVELA International or third-parties \n"
			+"(b)Not to infringe any patent, trade mark, trade secret, copyright, database right or other intellectual property rights of any person or entity \n"
			+"(c)Not to impersonate any person or entity, including, but not limited to, a TRAVELA official, or falsely state or otherwise misrepresent your affiliation with a person or entity \n"
			+"(d)To comply with these general conditions of use.If you do not comply with these obligations, TRAVELA reserves the right to immediately prohibit and block access to its Application and to its network without damages.\n"

			+"\n Information on the Application \n"
			+"While TRAVELA has made every effort to ensure that the information contained in a TRAVELA Application is correct at the time of going live, TRAVELA cannot be held responsible for any errors or omissions or any information which may be incomplete, inaccurate or may have become out of date."

			+ "\n \n Links to third parties Applications \n"
			+"There are several places throughout TRAVELA Applications that may link you to other Applications that do not operate under TRAVELA Applications information privacy practices. When you click through to these Applications, TRAVELA Applications information privacy practices no longer apply. We recommend that you examine the privacy statements for all third party Applications to understand their procedures for collecting, using, and disclosing your information."

			+"\n \n Disclaimer of Warranty \n"
			+ "Your use of a TRAVELA Application is at your sole risk. This Application is provided on an AS IS and AS AVAILABLE basis. TRAVELA makes no warranty or representation that (i) the Application will meet your requirements, (ii) it will be uninterrupted, timely, secure, or error-free, (iii) the results that may be obtained from the use of a TRAVELA Application will be accurate or reliable, (iv) the quality of any products, services, information, or other material purchased or obtained by you through the service will meet your expectations, and (v) any errors will be corrected. You will be solely responsible for any damage to your mobile system or loss of data that results from the use of the Application. No advice or information, whether oral or written, obtained by you from TRAVELA or through or from a TRAVELA Application will create any warranty or other obligation not expressly stated in the relevant terms and conditions. Although reasonable precautions are taken to protect the security and integrity of wireless internet and network access, TRAVELA cannot guarantee that use of a wireless connection will be secure. Accordingly, you agree to use such services at your own discretion and risk and acknowledge that you are solely responsible for any damage to your computer system or loss of data that results. To the fullest extent permitted by applicable law, TRAVELA expressly disclaims all warranties, conditions and other terms of any kind, whether express or implied, including, but not limited to any implied term of merchantability, satisfactory quality, fitness for a particular purpose, and any term as to the provision of services to a standard of reasonable care and skill or as to non-infringement of any intellectual property right."

			+ "\n \n Limitation of Liability \n"
			+ "Some jurisdictions do not allow the exclusion of certain warranties or the limitation or exclusion of liability for incidental or consequential damages. Accordingly, some of the above limitations set out in this article may not apply. In particular, nothing in these Terms & Conditions will affect the statutory rights of any consumer or exclude or restrict any liability for death or personal injury arising from the negligence or fraud of TRAVELA. You expressly acknowledge and agree that TRAVELA, its officers, directors, employees will not be liable for any direct, indirect, incidental, special, consequential or exemplary damages, including but not limited to, damages for loss of profits, goodwill, use, data or other intangible losses (even if TRAVELA hasbeen advised of the possibility of such damages), resulting from use of a TRAVELA Application. You expressly acknowledge and agree that TRAVELA will not be liable for the use or the inability to use a TRAVELA Application; (ii) the cost of procurement of substitute goods and services resulting from any goods, data, information or services purchased or obtained or messages received or transactions entered into through or from a TRAVELA Application; (iii) unauthorised access to or alteration of your transmissions or data; (iv) statements or conduct of any third party on a TRAVELA Application."

			+ "\n \n Privacy Policy \n"
			+ "The terms of the TRAVELA Privacy Policy are incorporated into these Terms and Conditions. You agree to the use of personal information by TRAVELA in accordance with the terms of and for the purposes set forth in TRAVELA Applications Privacy Policy."
			
			+ "\n \n Security Policy \n"
			+ "TRAVELA uses secure technology in order to safeguard personal information. TRAVELA complies with the procedures and security standards as further set out in the TRAVELA Security Policy."

			+ "\n \n General \n"
			+ "Any failure by TRAVELA to exercise or enforce any right or provision of these Terms & Conditions will not constitute a waiver of such right or provision. If any provision of these Terms & Conditions is found by a court of competent jurisdiction to be invalid, the parties agree that the court should endeavour to give effect to the parties' intentions as reflected in the provision, and the other provisions of the Terms & Conditions remain in full force and effect. The section titles in the Terms & Conditions are for convenience only and have no legal or contractual effect";

		super.onCreate(savedInstanceState);
		setContentView(R.layout.terms_and_conditions);
		b=(Button)findViewById(R.id.button1);
		t1=(TextView)findViewById(R.id.textView1);
		t1.setText(s);
		t1.setMovementMethod(new ScrollingMovementMethod());
		b.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

}
