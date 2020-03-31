package com.meteor.remote.core.interfaces;

/**
 * @brief The IconProvider class is a lookup table
 * between a string tag and a relevant icon.
 * This class also allows core functionality to not depend
 * on Icon class that requires GUI library
 */
public interface IconProvider {
   Object iconForTag( String iconTag);
};

