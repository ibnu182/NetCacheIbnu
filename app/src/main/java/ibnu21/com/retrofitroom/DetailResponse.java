package ibnu21.com.retrofitroom;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailResponse implements Parcelable{

    public Integer userId;
    public Integer id;
    public String title;
    public String body;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userId);
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
    }

    public DetailResponse() {
    }

    protected DetailResponse(Parcel in) {
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.body = in.readString();
    }

    public static final Creator<DetailResponse> CREATOR = new Creator<DetailResponse>() {
        @Override
        public DetailResponse createFromParcel(Parcel source) {
            return new DetailResponse(source);
        }

        @Override
        public DetailResponse[] newArray(int size) {
            return new DetailResponse[size];
        }
    };
}
