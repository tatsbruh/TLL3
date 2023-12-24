package com.tll3.Misc;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class Monsoon {
    public static class StartMonsoon extends Event {
        private final Cause cause;

        public StartMonsoon(Cause cae){
            cause = cae;
        }
        private static final HandlerList HANDLERS_LIST = new HandlerList();

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLERS_LIST;
        }

        public static HandlerList getHandlerList() {
            return HANDLERS_LIST;
        }

        public Cause getCause() {
            return cause;
        }

        public enum Cause{
            NATURAL,
            DEATH,
            COMMAND,
            UNKNOWN
        }

    }
    public static class StopMonsoon extends Event {
        private final StopMonsoon.Cause cause;

        public StopMonsoon(StopMonsoon.Cause cae){
            cause = cae;
        }
        private static final HandlerList HANDLERS_LIST = new HandlerList();

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLERS_LIST;
        }

        public static HandlerList getHandlerList() {
            return HANDLERS_LIST;
        }
        public enum Cause{
            NATURAL,
            COMMAND,
            UNKNOWN
        }
    }
}
