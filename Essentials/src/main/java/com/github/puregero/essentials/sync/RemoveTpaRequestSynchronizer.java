package com.github.puregero.essentials.sync;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import java.io.Serializable;
import java.util.UUID;

public class RemoveTpaRequestSynchronizer extends MultiServerSynchronizer<RemoveTpaRequestSynchronizer.Notification> {

    private final Essentials essentials;
    private boolean handling;

    public RemoveTpaRequestSynchronizer(Essentials essentials) {
        super(essentials, "essentials:remove_tpa_request");
        this.essentials = essentials;
        this.addHandler(this::handle);
    }

    private void handle(Notification notification) {
        User user = essentials.getUser(notification.getUuid());
        String username = notification.getUsername();

        handling = true;
        user.removeTpaRequest(username);
        handling = false;
    }

    public void notify(User user, String username) {
        if (!handling) {
            super.notify(new Notification(user, username));
        }
    }

    public static class Notification implements Serializable {
        private final UUID uuid;
        private final String username;

        public Notification(User user, String username) {
            this.uuid = user.getUUID();
            this.username = username;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getUsername() {
            return username;
        }
    }

}
