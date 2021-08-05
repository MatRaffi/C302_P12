package sg.edu.rp.s19024292.c302_p12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class IncidentAdapter extends ArrayAdapter<Incident> {

    ArrayList<Incident> al;
    Context context;

    public IncidentAdapter(Context context, int resource, ArrayList<Incident> objects) {
        super(context, resource, objects);
        al = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.tvHeader);
        TextView tvDesc = (TextView) rowView.findViewById(R.id.tvDesc);

        Incident incident = al.get(position);

        tvName.setText(incident.getType());
        tvDesc.setText(incident.getMessage());

        return rowView;
    }
}
