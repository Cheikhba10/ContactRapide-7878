package com.contactrapide.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.*;

public class ProfileActivity extends Activity {

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String value, float size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        t.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        return t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(248, 250, 249));

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(dp(22), dp(28), dp(22), dp(30));

        TextView badge = text("24/24", 34, Color.WHITE, true);
        badge.setGravity(Gravity.CENTER);
        badge.setBackgroundColor(Color.rgb(0, 105, 70));

        page.addView(badge, new LinearLayout.LayoutParams(
                dp(105), dp(105)
        ));

        TextView nom = text("24/24 AGENCY", 28,
                Color.rgb(0, 90, 55), true);
        nom.setPadding(0, dp(18), 0, dp(5));
        page.addView(nom);

        TextView slogan = text(
                "La tranquillité d'esprit n'a pas de prix",
                17, Color.DKGRAY, false
        );
        slogan.setPadding(0, 0, 0, dp(22));
        page.addView(slogan);

        TextView description = text(
                "Agence de services et d'assistance\n" +
                "disponible 24h/24 au Sénégal.",
                17, Color.rgb(55, 65, 81), false
        );
        description.setPadding(0, 0, 0, dp(20));
        page.addView(description);

        TextView telephone = text(
                "📞 76 130 13 30",
                21, Color.rgb(0, 90, 55), true
        );
        page.addView(telephone);

        TextView services = text(
                "NOS SERVICES\n\n" +
                "👶 Nanny / Garde d'enfants\n\n" +
                "🧹 Ménage / Nettoyage\n\n" +
                "🍽️ Cuisine\n\n" +
                "🚗 Chauffeur\n\n" +
                "🌳 Jardinage",
                17, Color.rgb(31, 41, 55), false
        );
        services.setPadding(0, dp(25), 0, dp(5));
        page.addView(services);

        Button appel = new Button(this);
        appel.setText("📞  APPELER L'AGENCE");
        appel.setTextSize(15);
        appel.setTextColor(Color.WHITE);
        appel.setBackgroundColor(Color.rgb(0, 90, 55));

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        -1, dp(55)
                );
        buttonParams.setMargins(0, dp(22), 0, 0);

        page.addView(appel, buttonParams);

        appel.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:761301330")
            );
            startActivity(intent);
        });

        Button whatsapp = new Button(this);
        whatsapp.setText("💬  CONTACTER SUR WHATSAPP");
        whatsapp.setTextSize(15);
        whatsapp.setTextColor(Color.WHITE);
        whatsapp.setBackgroundColor(Color.rgb(0, 105, 70));

        LinearLayout.LayoutParams whatsappParams =
                new LinearLayout.LayoutParams(
                        -1, dp(55)
                );
        whatsappParams.setMargins(0, dp(10), 0, 0);

        page.addView(whatsapp, whatsappParams);

        whatsapp.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://wa.me/221761301330")
                );
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(
                        ProfileActivity.this,
                        "WhatsApp n'est pas disponible",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        scroll.addView(page);
        setContentView(scroll);
    }
}
