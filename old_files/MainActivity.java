package com.contactrapide.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    private final String PHONE = "761301330";
    private final String WHATSAPP = "221761301330";

    LinearLayout page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnUrgence).setOnClickListener(v -> call());
        findViewById(R.id.navWhatsApp).setOnClickListener(v -> whatsapp());
        findViewById(R.id.navDemander).setOnClickListener(v -> demander());
        findViewById(R.id.navProfil).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        findViewById(R.id.serviceNanny).setOnClickListener(v ->
                service("👶 Nanny / Garde d'enfants",
                        "Garde d'enfants, accompagnement et surveillance."));

        findViewById(R.id.serviceMenage).setOnClickListener(v ->
                service("🍽️ Ménage & Cuisine",
                        "Nettoyage, entretien et préparation des repas."));

        findViewById(R.id.serviceChauffeur).setOnClickListener(v ->
                service("🚗 Chauffeur & Sécurité",
                        "Chauffeur professionnel et services de sécurité."));

        findViewById(R.id.servicePolyvalent).setOnClickListener(v ->
                service("🏠 Personnel Polyvalent",
                        "Personnel pour différentes tâches à domicile."));
    }

    private void call() {
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + PHONE)));
    }

    private void whatsapp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://wa.me/" + WHATSAPP)));
        } catch (Exception e) {
            Toast.makeText(this,
                    "WhatsApp n'est pas installé",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void service(String titre, String description) {

        page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(25, 40, 25, 25);
        page.setBackgroundColor(Color.rgb(247,249,252));

        TextView title = new TextView(this);
        title.setText(titre);
        title.setTextSize(26);
        title.setTextColor(Color.rgb(11,93,59));
        title.setGravity(17);
        title.setPadding(0,20,0,25);

        TextView text = new TextView(this);
        text.setText(description);
        text.setTextSize(18);
        text.setTextColor(Color.DKGRAY);
        text.setPadding(0,20,0,30);

        Button demander = new Button(this);
        demander.setText("📝 Demander ce service");

        Button retour = new Button(this);
        retour.setText("← Retour");

        page.addView(title);
        page.addView(text);
        page.addView(demander);
        page.addView(retour);

        setContentView(page);

        demander.setOnClickListener(v -> demanderService(titre));
        retour.setOnClickListener(v -> {
            setContentView(R.layout.activity_main);
            onCreate(null);
        });
    }

    private void demander() {
        demanderService("Service général");
    }

    private void demanderService(String service) {

        page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(25,30,25,25);

        TextView title = new TextView(this);
        title.setText("📝 Demande de service");
        title.setTextSize(25);
        title.setTextColor(Color.rgb(11,93,59));

        EditText nom = new EditText(this);
        nom.setHint("Votre nom");

        EditText telephone = new EditText(this);
        telephone.setHint("Votre téléphone");
        telephone.setInputType(2);

        EditText adresse = new EditText(this);
        adresse.setHint("Quartier / adresse");

        EditText message = new EditText(this);
        message.setHint("Votre demande");
        message.setMinLines(4);

        Button envoyer = new Button(this);
        envoyer.setText("💬 Envoyer sur WhatsApp");

        Button retour = new Button(this);
        retour.setText("← Retour");

        page.addView(title);
        page.addView(nom);
        page.addView(telephone);
        page.addView(adresse);
        page.addView(message);
        page.addView(envoyer);
        page.addView(retour);

        setContentView(page);

        envoyer.setOnClickListener(v -> {

            String texte =
                    "Bonjour 24/24 AGENCY,%0A%0A" +
                    "Nom : " + nom.getText() + "%0A" +
                    "Téléphone : " + telephone.getText() + "%0A" +
                    "Adresse : " + adresse.getText() + "%0A" +
                    "Service : " + service + "%0A" +
                    "Demande : " + message.getText();

            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://wa.me/" + WHATSAPP +
                                "?text=" + texte)));
            } catch (Exception e) {
                Toast.makeText(this,
                        "Impossible d'ouvrir WhatsApp",
                        Toast.LENGTH_SHORT).show();
            }
        });

        retour.setOnClickListener(v -> {
            setContentView(R.layout.activity_main);
            onCreate(null);
        });
    }
}